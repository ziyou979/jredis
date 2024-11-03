package org.zy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;
import org.zy.command.CommandDecoder;
import org.zy.command.CommandHandler;
import org.zy.factory.ChannelOptionFactory;
import org.zy.factory.NamedThreadFactory;
import org.zy.netty.channel.ChannelOption;
import org.zy.redis.RedisDb;
import org.zy.resp.RespEncoder;
import org.zy.util.PropertiesUtil;

import java.net.InetSocketAddress;

import static io.netty.channel.ChannelOption.*;

/**
 * <p>
 * Redis服务入口
 * </p>
 *
 * @author ZPY
 * @date 2023/6/8 21:04
 */
@Slf4j
public class RedisApplication {

    private final RedisDb redisDb = new RedisDb();

    private final ServerBootstrap serverBootstrap = new ServerBootstrap();

    private final EventExecutorGroup redisExecutor;

    private final ChannelOption channelOption;

    public RedisApplication() {
        channelOption = ChannelOptionFactory.create();
        // 单线程
        redisExecutor = new NioEventLoopGroup(1, NamedThreadFactory.newThreadFactory("redis-main"));
    }


    public static void main(String[] args) {
        new RedisApplication().start();
    }

    public void start() {
        serverBootstrap
                // 设置 boss 和 worker 线程组
                .group(channelOption.boss(), channelOption.selectors())
                // 服务器通道类型
                .channel(channelOption.getChannelClass())
                // 日志处理器
                .handler(new LoggingHandler(LogLevel.INFO))
                // TCP 选项
                .childOption(SO_KEEPALIVE, PropertiesUtil.getTcpKeepAlive())
                .option(SO_BACKLOG, 1024)
                .option(SO_REUSEADDR, true)
                // 设置服务器监听地址和端口
                .localAddress(new InetSocketAddress(PropertiesUtil.getNodeAddress(), PropertiesUtil.getNodePort()))
                // 初始化子通道处理器
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();
                        channelPipeline.addLast(
                                new RespEncoder(),
                                // todo aof
                                new CommandDecoder()
                        );
                        // 主线程
                        channelPipeline.addLast(redisExecutor, new CommandHandler(redisDb));
                    }
                });
        try {
            ChannelFuture sync = serverBootstrap.bind().sync().channel().closeFuture().sync();
            log.info("RedisApplication.start redis 启动成功，IP 地址：{}", sync.channel().localAddress());
        } catch (InterruptedException e) {
            log.error("RedisApplication.start redis 服务启动异常", e);
        }
    }

    public void close() {
        try (EventLoopGroup boss = channelOption.boss();
             EventLoopGroup selectors = channelOption.selectors()) {
            boss.shutdownGracefully();
            selectors.shutdownGracefully();
            redisExecutor.shutdownGracefully();
        } catch (Exception e) {
            log.error("RedisApplication.close redis 服务关闭异常", e);
        }
    }
}
