package org.zy.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.zy.command.RedisCommand;
import org.zy.exception.JredisException;
import org.zy.redis.RedisDbList;
import org.zy.resp.data.Error;
import org.zy.util.TraceUtil;

@Slf4j
public class CommandHandler extends SimpleChannelInboundHandler<RedisCommand> {

    private final RedisDbList redisDbs;

    public CommandHandler(RedisDbList redisDbs) {
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
        if (cause instanceof JredisException jredisException) {
            ctx.writeAndFlush(new Error(jredisException.getReason()));
        } else {
            // 非法异常
            ctx.writeAndFlush(new Error("Err system error"));
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        ctx.close();
    }
}
