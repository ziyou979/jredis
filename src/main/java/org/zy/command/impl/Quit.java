package org.zy.command.impl;

import io.netty.channel.ChannelHandlerContext;
import org.zy.command.CommandType;
import org.zy.command.RedisCommand;
import org.zy.redis.RedisDbList;
import org.zy.redis.RedisServer;
import org.zy.resp.data.Resp;
import org.zy.resp.data.SimpleString;

public class Quit implements RedisCommand {
    @Override
    public CommandType type() {
        return CommandType.QUIT;
    }

    @Override
    public void setContent(Resp[] resp) {

    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDbList redisDbs) {
        // 移除客户端连接
        RedisServer.removeClient(ctx.channel());
        ctx.writeAndFlush(SimpleString.OK);
        ctx.close();
    }
}
