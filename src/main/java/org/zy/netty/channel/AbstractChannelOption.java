package org.zy.netty.channel;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;

public abstract class AbstractChannelOption<E extends EventLoopGroup> implements ChannelOption {

    protected final E boss;

    protected final E selectors;

    protected AbstractChannelOption(E boss, E selectors) {
        this.boss = boss;
        this.selectors = selectors;
    }

    @Override
    public E boss() {
        return boss;
    }

    @Override
    public E selectors() {
        return selectors;
    }

    @Override
    public abstract Class<? extends ServerChannel> getChannelClass();
}
