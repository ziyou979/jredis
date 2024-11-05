package org.zy.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.zy.command.RedisCommand;
import org.zy.redis.RedisDb;
import org.zy.util.TraceUtil;

import java.util.List;

@Slf4j
public class CommandHandler extends SimpleChannelInboundHandler<RedisCommand> {

    private final List<RedisDb> redisDbs;

    public CommandHandler(List<RedisDb> redisDbs) {
        this.redisDbs = redisDbs;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RedisCommand redisCommand) {
        log.debug("channelRead0 读取命令：{}，traceId：{}", redisCommand.type(), TraceUtil.getTraceId());
        redisCommand.handle(channelHandlerContext, redisDbs);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("CommandHandler.exceptionCaught 发生异常", cause);
        ctx.close();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        ctx.close();
    }
}
