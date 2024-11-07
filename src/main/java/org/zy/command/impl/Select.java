package org.zy.command.impl;

import io.netty.channel.ChannelHandlerContext;
import org.zy.command.AbstractRedisCommand;
import org.zy.command.CommandType;
import org.zy.exception.JredisException;
import org.zy.redis.RedisDbList;
import org.zy.redis.RedisServer;
import org.zy.resp.data.SimpleString;

public class Select extends AbstractRedisCommand {
    @Override
    public CommandType type() {
        return CommandType.SELECT;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDbList redisDbs) {
        try {
            int db = Integer.parseInt(getCommandInfo(1));
            if (db >= 0 && db < redisDbs.size()) {
                RedisServer.setClientDb(ctx.channel(), db);
                ctx.writeAndFlush(SimpleString.OK);
            } else {
                throw JredisException.invalidIndex();
            }
        } catch (NumberFormatException e) {
            throw JredisException.invalidInt();
        }
    }
}
