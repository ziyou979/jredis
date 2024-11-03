package org.zy.factory;


import lombok.NonNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 自定义线程工厂
 * </p>
 *
 * @author ZPY
 * @date 2024/10/31
 */
public class NamedThreadFactory implements ThreadFactory {

    protected static final AtomicInteger POOL_SEQ = new AtomicInteger(0);

    protected final AtomicInteger mThreadNum = new AtomicInteger(1);

    protected final String mPrefix;

    protected final boolean mDaemon;

    protected NamedThreadFactory() {
        this("jredis-default-thread" + POOL_SEQ.getAndIncrement(), false);
    }

    protected NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    protected NamedThreadFactory(String prefix, boolean daemon) {
        mPrefix = prefix + "-thread-";
        mDaemon = daemon;
    }

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        String name = mPrefix + mThreadNum.getAndIncrement();
        Thread thread = new Thread(runnable, name);
        thread.setDaemon(mDaemon);
        return thread;
    }

    public static ThreadFactory newThreadFactory() {
        return new NamedThreadFactory();
    }

    public static ThreadFactory newThreadFactory(String prefix) {
        return new NamedThreadFactory(prefix);
    }

    public static ThreadFactory newThreadFactory(String prefix, boolean daemon) {
        return new NamedThreadFactory(prefix, daemon);
    }

    public static ThreadFactory newVirtualThreadFactory(String prefix) {
        return Thread.ofVirtual().name(prefix + "-virtualThread-", 0).factory();
    }
}
