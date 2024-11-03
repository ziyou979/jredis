package org.zy.command.impl;

import io.netty.channel.ChannelHandlerContext;
import org.zy.command.CommandType;
import org.zy.command.RedisCommand;
import org.zy.redis.RedisDb;
import org.zy.resp.data.Resp;
import org.zy.resp.data.SimpleString;

public class Auth implements RedisCommand {
    @Override
    public CommandType type() {
        return CommandType.AUTH;
    }

    @Override
    public void setContent(Resp[] resp) {

    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDb redisDb) {
        ctx.writeAndFlush(SimpleString.OK);
    }

}
