package org.zy.netty.channel;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;

public interface ChannelOption {
    /**
     * @return 返回获取tcp线程
     */
    EventLoopGroup boss();

    /**
     * @return 返回处理tcp线程
     */
    EventLoopGroup selectors();

    /**
     * @return 返回管道类型
     */
    Class<? extends ServerChannel> getChannelClass();
}
