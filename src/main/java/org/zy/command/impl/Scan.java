package org.zy.command.impl;

import io.netty.channel.ChannelHandlerContext;
import lombok.ToString;
import org.zy.command.CommandType;
import org.zy.command.RedisCommand;
import org.zy.redis.RedisDb;
import org.zy.redis.RedisDbList;
import org.zy.resp.data.Array;
import org.zy.resp.data.BulkString;
import org.zy.resp.data.Resp;
import org.zy.util.PropertiesUtil;

import java.util.*;

@ToString
public class Scan implements RedisCommand {
    @Override
    public CommandType type() {
        return CommandType.SCAN;
    }

    @Override
    public void setContent(Resp[] resp) {

    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDbList redisDbs) {
        int dbNum = PropertiesUtil.getDatabases();
        // 返回结果数量为数据库的数量*2
        Resp[] resp = new Resp[dbNum << 1];
        for (int i = 0; i < dbNum; i++) {
            int index = i << 1;
            resp[index] = new BulkString(String.valueOf(i));
            RedisDb redisDb = redisDbs.get(i);
            if (Objects.nonNull(redisDb)) {
                resp[index + 1] = new Array(redisDb.keys().stream().map(BulkString::new).toArray(BulkString[]::new));
            }
        }
        ctx.writeAndFlush(new Array(resp));
    }

}
