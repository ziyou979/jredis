package org.zy.command.impl;

import io.netty.channel.ChannelHandlerContext;
import org.zy.command.AbstractRedisCommand;
import org.zy.command.CommandType;
import org.zy.datatype.RedisData;
import org.zy.redis.RedisDb;
import org.zy.redis.RedisDbList;
import org.zy.resp.data.Integer;

import java.util.Objects;

public class Ttl extends AbstractRedisCommand {
    @Override
    public CommandType type() {
        return CommandType.TTL;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDbList redisDbs) {
        String key = getCommandInfo(1);
        RedisDb redisDb = redisDbs.get(ctx.channel());
        RedisData redisData = redisDb.get(key);
        if (Objects.isNull(redisData)) {
            // 已过期
            ctx.writeAndFlush(new Integer(-2));
        } else if (redisData.getTimeout() == -1) {
            // 永不过期
            ctx.writeAndFlush(new Integer(-1));
        } else {
            // 返回剩余有效秒数
            long seconds = (redisData.getTimeout() - System.currentTimeMillis()) / 1000;
            ctx.writeAndFlush(new Integer(seconds));
        }
    }
}
