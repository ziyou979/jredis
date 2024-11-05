package org.zy.command.impl;

import io.netty.channel.ChannelHandlerContext;
import lombok.ToString;
import org.zy.command.AbstractRedisCommand;
import org.zy.command.CommandType;
import org.zy.exception.JredisException;
import org.zy.redis.RedisDb;
import org.zy.redis.RedisServer;
import org.zy.resp.data.SimpleString;

import java.util.List;

@ToString
public class Client extends AbstractRedisCommand {

    @Override
    public CommandType type() {
        return CommandType.CLIENT;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, List<RedisDb> redisDbs) {
        String subCommand = getCommandInfo(1);
        switch (subCommand) {
            case "setname" -> {
                String name = getCommandInfo(2);
                RedisServer.addClient(name, ctx.channel());
                ctx.writeAndFlush(SimpleString.OK);
            }
            case "getname" -> {
                String name = RedisServer.getClientName(ctx.channel());
                ctx.writeAndFlush(new SimpleString(name));
            }
            default -> throw JredisException.invalidArgument(subCommand);
        }
    }

}
