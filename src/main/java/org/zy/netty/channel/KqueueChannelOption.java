package org.zy.netty.channel;

import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import org.zy.factory.NamedThreadFactory;

public class KqueueChannelOption extends AbstractChannelOption<KQueueEventLoopGroup> {

    public KqueueChannelOption(KQueueEventLoopGroup boss, KQueueEventLoopGroup selectors) {
        super(boss, selectors);
    }

    public KqueueChannelOption() {
        super(new KQueueEventLoopGroup(NamedThreadFactory.newVirtualThreadFactory("kqueue-boss-")),
                new KQueueEventLoopGroup(NamedThreadFactory.newVirtualThreadFactory("kqueue-selector-")));
    }

    @Override
    public Class<KQueueServerSocketChannel> getChannelClass() {
        return KQueueServerSocketChannel.class;
    }
}
