package org.zy.command.impl.string;

import io.netty.channel.ChannelHandlerContext;
import org.zy.command.AbstractRedisCommand;
import org.zy.command.CommandType;
import org.zy.datatype.RedisString;
import org.zy.exception.JredisException;
import org.zy.redis.RedisDb;
import org.zy.redis.RedisDbList;
import org.zy.resp.data.BulkString;
import org.zy.resp.data.Resp;
import org.zy.resp.data.SimpleString;

public class Set extends AbstractRedisCommand {

    private boolean notExistSet;
    private boolean existSet;
    private long timeout = -1;

    @Override
    public CommandType type() {
        return CommandType.SET;
    }

    @Override
    public void setContent(Resp[] resp) {
        super.setContent(resp);
        int index = 3;
        while (index < resp.length) {
            String commandInfo = getCommandInfo(index);
            index++;
            if (commandInfo.startsWith("EX")) {
                String seconds = getCommandInfo(index);
                timeout = tryParseTimeout(seconds) * 1000;
            } else if (commandInfo.startsWith("PX")) {
                String seconds = getCommandInfo(index);
                timeout = tryParseTimeout(seconds);
            } else if (commandInfo.equals("NX")) {
                notExistSet = true;
            } else if (commandInfo.equals("XX")) {
                existSet = true;
            }
        }
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDbList redisDbs) {
        String key = getCommandInfo(1);
        String val = getCommandInfo(2);
        RedisDb redisDb = redisDbs.get(ctx.channel());
        if (notExistSet && redisDb.exists(key) > 0) {
            ctx.writeAndFlush(BulkString.NULL);
        } else if (existSet && redisDb.exists(key) <= 0) {
            ctx.writeAndFlush(BulkString.NULL);
        } else {
            if (timeout != -1) {
                // 过期时间=当前时间+有效时长
                timeout += System.currentTimeMillis();
            }
            RedisString stringData = new RedisString(val, timeout);
            redisDb.put(key, stringData);
            ctx.writeAndFlush(SimpleString.OK);
        }
    }

    private long tryParseTimeout(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            throw JredisException.invalidTimeout();
        }
    }
}
