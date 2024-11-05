package org.zy.netty.channel;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.zy.factory.NamedThreadFactory;

public class NioChannelOption extends AbstractChannelOption<NioEventLoopGroup> {

    public NioChannelOption(NioEventLoopGroup boss, NioEventLoopGroup selectors) {
        super(boss, selectors);
    }

    public NioChannelOption() {
        super(new NioEventLoopGroup(NamedThreadFactory.newVirtualThreadFactory("nio-boss")),
                new NioEventLoopGroup(NamedThreadFactory.newVirtualThreadFactory("nio-selector")));
    }

    @Override
    public Class<NioServerSocketChannel> getChannelClass() {
        return NioServerSocketChannel.class;
    }
}
