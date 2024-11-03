package org.zy.command;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.zy.redis.RedisDb;
import org.zy.util.TraceUtil;

@Slf4j
public class CommandHandler extends SimpleChannelInboundHandler<RedisCommand> {

    private final RedisDb redisDb;

    public CommandHandler(RedisDb redisDb) {
        this.redisDb = redisDb;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RedisCommand redisCommand) {
        log.debug("receive command: {}, traceId: {}", redisCommand.type(), TraceUtil.getTraceId());
        redisCommand.handle(channelHandlerContext, redisDb);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("exception caught", cause);
        ctx.close();
    }
}
