package org.zy.command.impl.string;

import io.netty.channel.ChannelHandlerContext;
import org.zy.command.AbstractRedisCommand;
import org.zy.command.CommandType;
import org.zy.datatype.RedisData;
import org.zy.datatype.RedisString;
import org.zy.exception.JredisException;
import org.zy.redis.RedisDb;
import org.zy.redis.RedisDbList;
import org.zy.resp.data.BulkString;

import java.util.Objects;

public class Get extends AbstractRedisCommand {

    @Override
    public CommandType type() {
        return CommandType.GET;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDbList redisDbs) {
        String key = getCommandInfo(1);
        RedisDb redisDb = redisDbs.get(ctx.channel());
        RedisData data = redisDb.get(key);
        if (Objects.isNull(data)) {
            ctx.writeAndFlush(BulkString.NULL);
        } else if (data instanceof RedisString redisString){
            ctx.writeAndFlush(new BulkString(redisString.get()));
        } else {
            throw JredisException.invalidType();
        }
    }
}
