package org.zy.netty.channel;

import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import org.zy.factory.NamedThreadFactory;

public class EpollChannelOption extends AbstractChannelOption<EpollEventLoopGroup> {

    public EpollChannelOption(EpollEventLoopGroup boss, EpollEventLoopGroup selectors) {
        super(boss, selectors);
    }

    public EpollChannelOption() {
        super(new EpollEventLoopGroup(NamedThreadFactory.newVirtualThreadFactory("epoll-boss-")),
                new EpollEventLoopGroup(NamedThreadFactory.newVirtualThreadFactory("epoll-selector-")));
    }

    @Override
    public Class<EpollServerSocketChannel> getChannelClass() {
        return EpollServerSocketChannel.class;
    }
}
