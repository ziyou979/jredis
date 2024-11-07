package org.zy.redis;

import io.netty.channel.Channel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.kqueue.KQueue;
import lombok.NonNull;
import org.zy.util.ObjectUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import java.lang.management.OperatingSystemMXBean;
import java.net.SocketAddress;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RedisServer {

    private static final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    private static final OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

    private static final Map<SocketAddress, RedisClientInfo> clients = new ConcurrentHashMap<>();

    private static final long startTime = System.currentTimeMillis();

    public static void addClient(String clientName, Channel channel) {
        if (ObjectUtil.hasNull(clientName, channel)) {
            return;
        }
        RedisClientInfo clientInfo = RedisClientInfo.builder()
                .clientName(clientName)
                // 默认使用数据库0
                .db(0)
                .build();
        clients.put(channel.remoteAddress(), clientInfo);
    }

    public static void removeClient(Channel channel) {
        if (Objects.isNull(channel)) {
            return;
        }
        clients.remove(channel.remoteAddress());
    }

    public static String getClientName(Channel channel) {
        if (Objects.isNull(channel)) {
            return null;
        }
        RedisClientInfo clientInfo = clients.get(channel.remoteAddress());
        return Objects.isNull(clientInfo) ? null : clientInfo.getClientName();
    }

    public static int getClientDb(Channel channel) {
        if (Objects.isNull(channel)) {
            return 0;
        }
        RedisClientInfo clientInfo = clients.get(channel.remoteAddress());
        return Objects.isNull(clientInfo) ? 0 : clientInfo.getDb();
    }

    public static void setClientDb(@NonNull Channel channel, int db) {
        RedisClientInfo clientInfo = clients.get(channel.remoteAddress());
        if (Objects.nonNull(clientInfo)) {
            clientInfo.setDb(db);
        }
    }

    public static String getUsedHeapMemory() {
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        return String.valueOf(heapMemoryUsage.getUsed());
    }

    public static String getOsName() {
        return System.getProperty("os.name");
    }

    public static String getPid() {
        return String.valueOf(ProcessHandle.current().pid());
    }

    public static String getConnectedClients() {
        return String.valueOf(clients.size());
    }

    public static String getJdkVersion() {
        return System.getProperty("java.version");
    }

    public static String getUptimeInSeconds() {
        return String.valueOf((System.currentTimeMillis() - startTime) / 1000);
    }

    public static String getUptimeInDays() {
        return String.valueOf((System.currentTimeMillis() - startTime) / 86400000);
    }

    public static String getMultiplexingApi() {
        if (KQueue.isAvailable()) {
            return "kqueue";
        }
        if (Epoll.isAvailable()) {
            return "epoll";
        }
        return "nio";
    }

    public static String getJvmInfo() {
        return System.getProperty("java.vm.name") + " " + System.getProperty("java.vm.version");
    }

    public static String getOsArch() {
        return System.getProperty("os.arch");
    }

    public static String getCpuUsage() {
        return String.valueOf(operatingSystemMXBean.getSystemLoadAverage());

    }
}
