package org.zy.command.impl;

import io.netty.channel.ChannelHandlerContext;
import lombok.ToString;
import org.zy.command.CommandType;
import org.zy.command.RedisCommand;
import org.zy.redis.RedisDbList;
import org.zy.resp.data.BulkString;
import org.zy.resp.data.Resp;
import org.zy.redis.RedisServer;
import org.zy.util.IdUtil;
import org.zy.util.PropertiesUtil;

import java.util.HashMap;
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
    public void handle(ChannelHandlerContext ctx, RedisDbList redisDbs) {
        Map<String, String> infos = new HashMap<>();
        // Server
        infos.put("redis_version", "0.0.1.java");
        infos.put("redis_git_sha1", "00000000");
        infos.put("redis_git_dirty", "0");
        infos.put("redis_build_id", "zy-java-redis");
        infos.put("redis_mode", "standalone");
        infos.put("os", RedisServer.getOsName());
        infos.put("arch_bits", RedisServer.getOsArch());
        infos.put("multiplexing_api", RedisServer.getMultiplexingApi());
        infos.put("gcc_version", RedisServer.getJdkVersion());
        infos.put("process_id", RedisServer.getPid());
        infos.put("run_id", IdUtil.simpleUUID());
        infos.put("tcp_port", String.valueOf(PropertiesUtil.getNodePort()));
        infos.put("uptime_in_seconds", RedisServer.getUptimeInSeconds());
        infos.put("uptime_in_days", RedisServer.getUptimeInDays());
        // todo 内部调度频率（关闭超时客户端、过期缓存等频率，单位秒）
        infos.put("hz", "10");
        infos.put("lru_clock", null);
        infos.put("executable", null);
        infos.put("config_file", null);

        // Clients
        infos.put("connected_clients", RedisServer.getConnectedClients());

        // Memory
        infos.put("used_memory", RedisServer.getUsedHeapMemory());
        infos.put("mem_allocator", RedisServer.getJvmInfo());

        // CPU
        infos.put("used_cpu_sys", RedisServer.getCpuUsage());

        // Keyspace


        String infoString = infos.entrySet().stream()
                .map(entry -> entry.getKey() + COLON + entry.getValue() + CRLF)
                .collect(Collectors.joining());
        ctx.writeAndFlush(new BulkString(infoString));
    }
}
