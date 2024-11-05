package org.zy.command.impl;

import io.netty.channel.ChannelHandlerContext;
import lombok.ToString;
import org.zy.command.AbstractRedisCommand;
import org.zy.command.CommandType;
import org.zy.exception.JredisException;
import org.zy.redis.RedisDb;
import org.zy.resp.data.Array;
import org.zy.resp.data.BulkString;
import org.zy.resp.data.Resp;
import org.zy.util.ObjectUtil;
import org.zy.util.PropertiesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@ToString
public class Config extends AbstractRedisCommand {

    private String param;

    @Override
    public CommandType type() {
        return CommandType.CONFIG;
    }

    @Override
    public void setContent(Resp[] resp) {
        super.setContent(resp);
        String subCommand = getCommandInfo(1);
        if (resp.length != 3 || !"get".equals(subCommand)) {
            throw JredisException.invalidArgument(subCommand);
        }
        this.param = getCommandInfo(2);
    }

    @Override
    public void handle(ChannelHandlerContext ctx, List<RedisDb> redisDbs) {
        List<BulkString> res = new ArrayList<>();
        Properties properties = PropertiesUtil.getProperties();
        if (paramEquals("databases")) {
            res.add(new BulkString("databases"));
            res.add(new BulkString(ObjectUtil.cast(properties.get("databases"))));
        }
        if (paramEquals("appendonly")) {
            res.add(new BulkString("appendonly"));
            res.add(new BulkString(ObjectUtil.cast(properties.get("appendonly"))));
        }
        Resp[] array = res.toArray(new BulkString[0]);
        ctx.writeAndFlush(new Array(array));

    }

    private boolean paramEquals(String param) {
        return "*".equals(this.param) || param.equals(this.param);
    }
}
