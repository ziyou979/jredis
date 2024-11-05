package org.zy.command.impl;

import io.netty.channel.ChannelHandlerContext;
import lombok.ToString;
import org.zy.command.CommandType;
import org.zy.command.RedisCommand;
import org.zy.redis.RedisDb;
import org.zy.resp.data.BulkString;
import org.zy.resp.data.Resp;
import org.zy.redis.RedisServer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.zy.constant.CommonConstant.COLON;
import static org.zy.constant.CommonConstant.CRLF;

@ToString
public class Info implements RedisCommand {

    @Override
    public CommandType type() {
        return CommandType.INFO;
    }

    @Override
    public void setContent(Resp[] resp) {

    }

    @Override
    public void handle(ChannelHandlerContext ctx, List<RedisDb> redisDbs) {
        Map<String, String> infos = Map.of(
                "redis_version", "0.0.1.java",
                "redis_git_sha1", "00000000",
                "redis_git_dirty", "0",
                "redis_build_id", "zy-java-redis",
                "redis_mode", "standalone",
                "os", RedisServer.getOsName(),
                "process_id", RedisServer.getPid(),
                "used_memory", RedisServer.getUsedHeapMemory()
        );
        String infoString = infos.entrySet().stream()
                .map(entry -> entry.getKey() + COLON + entry.getValue() + CRLF)
                .collect(Collectors.joining());
        ctx.writeAndFlush(new BulkString(infoString));
    }
}
