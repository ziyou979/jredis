package org.zy.redis;

import io.netty.channel.Channel;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisServer {

    private static final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    private static final Map<Channel, String> clients = new ConcurrentHashMap<>();

    public static void addClient(String clientName, Channel channel) {
        clients.put(channel, clientName);
    }

    public static void removeClient(Channel channel) {
        clients.remove(channel);
    }

    public static String getClientName(Channel channel) {
        return clients.get(channel);
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
}
