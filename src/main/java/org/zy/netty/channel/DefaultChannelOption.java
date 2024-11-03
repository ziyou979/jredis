package org.zy.netty.channel;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.local.LocalServerChannel;
import org.zy.factory.NamedThreadFactory;

public class DefaultChannelOption extends AbstractChannelOption<DefaultEventLoopGroup> {

    public DefaultChannelOption(DefaultEventLoopGroup boss, DefaultEventLoopGroup selectors) {
        super(boss, selectors);
    }

    public DefaultChannelOption() {
        super(new DefaultEventLoopGroup(NamedThreadFactory.newVirtualThreadFactory("default-boss-")),
                new DefaultEventLoopGroup(NamedThreadFactory.newVirtualThreadFactory("default-selector-")));
    }

    @Override
    public Class<LocalServerChannel> getChannelClass() {
        return LocalServerChannel.class;
    }
}
