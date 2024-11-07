package org.zy.command.impl;

import io.netty.channel.ChannelHandlerContext;
import org.zy.command.AbstractRedisCommand;
import org.zy.command.CommandType;
import org.zy.datatype.*;
import org.zy.exception.JredisException;
import org.zy.redis.RedisDb;
import org.zy.redis.RedisDbList;
import org.zy.resp.data.SimpleString;

public class Type extends AbstractRedisCommand {
    @Override
    public CommandType type() {
        return CommandType.TYPE;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDbList redisDbs) {
        String key = getCommandInfo(1);
        RedisDb redisDb = redisDbs.get(ctx.channel());
        RedisData redisData = redisDb.get(key);
        switch (redisData) {
            case RedisString ignored -> ctx.writeAndFlush(new SimpleString("string"));
            case RedisList ignored -> ctx.writeAndFlush(new SimpleString("list"));
            case RedisSet ignored -> ctx.writeAndFlush(new SimpleString("set"));
            case RedisHash ignored -> ctx.writeAndFlush(new SimpleString("hash"));
            case null -> ctx.writeAndFlush(new SimpleString("none"));
            default -> throw JredisException.invalidType();
        }
    }
}
