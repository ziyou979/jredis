package org.zy.command.impl.string;

import io.netty.channel.ChannelHandlerContext;
import org.zy.command.AbstractRedisCommand;
import org.zy.command.CommandType;
import org.zy.datatype.RedisString;
import org.zy.exception.JredisException;
import org.zy.redis.RedisDb;
import org.zy.redis.RedisDbList;
import org.zy.resp.data.SimpleString;

public class Mset extends AbstractRedisCommand {
    @Override
    public CommandType type() {
        return CommandType.MSET;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDbList redisDbs) {
        String[] commandInfos = getCommandInfos(1);
        if (commandInfos.length % 2 != 0) {
            throw JredisException.invalidSyntax();
        }
        RedisDb redisDb = redisDbs.get(ctx.channel());
        for (int i = 0; i < commandInfos.length; i += 2) {
            redisDb.put(commandInfos[i], new RedisString(commandInfos[i + 1]));
        }
        ctx.writeAndFlush(SimpleString.OK);
    }


}
