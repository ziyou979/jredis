package org.zy.command.impl;

import io.netty.channel.ChannelHandlerContext;
import org.zy.command.CommandType;
import org.zy.command.RedisCommand;
import org.zy.redis.RedisDb;
import org.zy.resp.data.BulkString;
import org.zy.resp.data.Resp;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Map;
import java.util.stream.Collectors;

import static org.zy.constant.CommonConstant.COLON;
import static org.zy.constant.CommonConstant.CRLF;

public class Info implements RedisCommand {

    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    @Override
    public CommandType type() {
        return CommandType.INFO;
    }

    @Override
    public void setContent(Resp[] resp) {

    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDb redisDb) {
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        Map<String, String> infos = Map.of(
                "redis_version", "0.0.1.java",
                "redis_git_sha1", "00000000",
                "redis_git_dirty", "0",
                "redis_build_id", "zy-java-redis",
                "redis_mode", "standalone",
                "os", System.getProperty("os.name"),
                "process_id", String.valueOf(ProcessHandle.current().pid()),
                "used_memory", String.valueOf(heapMemoryUsage.getUsed())
        );
        String infoString = infos.entrySet().stream()
                .map(entry -> entry.getKey() + COLON + entry.getValue() + CRLF)
                .collect(Collectors.joining());
        ctx.writeAndFlush(new BulkString(infoString));
    }
}
