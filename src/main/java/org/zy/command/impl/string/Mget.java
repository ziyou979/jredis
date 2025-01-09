package org.zy.command.impl.string;

import io.netty.channel.ChannelHandlerContext;
import org.zy.command.AbstractRedisCommand;
import org.zy.command.CommandType;
import org.zy.datatype.RedisData;
import org.zy.datatype.RedisString;
import org.zy.exception.JredisException;
import org.zy.redis.RedisDb;
import org.zy.redis.RedisDbList;
import org.zy.resp.data.Array;
import org.zy.resp.data.BulkString;
import org.zy.resp.data.Resp;

import java.util.Arrays;
import java.util.Objects;

public class Mget extends AbstractRedisCommand {
    @Override
    public CommandType type() {
        return CommandType.MGET;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDbList redisDbs) {
        String[] keys = getCommandInfos(1);
        RedisDb redisDb = redisDbs.get(ctx.channel());
        Array respArray = new Array(Arrays.stream(keys).map(key -> {
            RedisData redisData = redisDb.get(key);
            if (Objects.isNull(redisData)) {
                return BulkString.NULL;
            } else if (redisData instanceof RedisString redisString) {
                return new BulkString(redisString.get());
            } else {
                throw JredisException.invalidType();
            }
        }).toArray(Resp[]::new));
        ctx.writeAndFlush(respArray);
    }
}
