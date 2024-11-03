package org.zy.factory;

import io.netty.channel.epoll.Epoll;
import io.netty.channel.kqueue.KQueue;
import org.zy.netty.channel.ChannelOption;
import org.zy.netty.channel.EpollChannelOption;
import org.zy.netty.channel.KqueueChannelOption;
import org.zy.netty.channel.NioChannelOption;


public class ChannelOptionFactory {
    public static ChannelOption create() {
        if (KQueue.isAvailable()) {
            return new KqueueChannelOption();
        }
        if (Epoll.isAvailable()) {
            return new EpollChannelOption();
        }
        return new NioChannelOption();

    }
}
