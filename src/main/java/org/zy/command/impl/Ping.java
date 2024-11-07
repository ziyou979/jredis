package org.zy.command.impl;

import io.netty.channel.ChannelHandlerContext;
import lombok.ToString;
import org.zy.command.CommandType;
import org.zy.command.RedisCommand;
import org.zy.redis.RedisDbList;
import org.zy.resp.data.Resp;
import org.zy.resp.data.SimpleString;

@ToString
public class Ping implements RedisCommand {
    @Override
    public CommandType type() {
        return CommandType.PING;
    }

    @Override
    public void setContent(Resp[] resp) {

    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDbList redisDbs) {
        ctx.writeAndFlush(new SimpleString("PONG"));
    }
}
