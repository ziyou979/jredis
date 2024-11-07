package org.zy.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.zy.command.RedisCommand;
import org.zy.exception.JredisException;
import org.zy.factory.CommandFactory;
import org.zy.factory.RespDecoderFactory;
import org.zy.resp.data.*;
import org.zy.resp.data.Error;
import org.zy.util.TraceUtil;

import java.lang.Integer;

@Slf4j
public class CommandDecoder extends LengthFieldBasedFrameDecoder {

    public CommandDecoder() {
        super(Integer.MAX_VALUE, 0, 4);
    }

    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        if (in.readableBytes() != 0) {
            int mark = in.readerIndex();
            try {
                Resp resp = RespDecoderFactory.decode(in);
                // 新的指令到来，生成新的 traceId
                log.debug("收到新的指令：{}，traceId: {}", resp, TraceUtil.newTraceId());
                RedisCommand command = null;
                switch (resp) {
                    case SimpleString simpleString -> command = CommandFactory.valueOf(simpleString);
                    case Array array -> command = CommandFactory.valueOf(array);
                    default -> ctx.writeAndFlush(new UnsupportedProtocolError());
                }
                log.debug("解析指令成功：{}，traceId: {}", command, TraceUtil.getTraceId());
                return command;
            } catch (JredisException jredisException) {
                ctx.writeAndFlush(new Error(jredisException.getReason()));
            } catch (Exception e) {
                log.error("decode 解析指令时发生异常，traceId: {}", TraceUtil.getTraceId(), e);
                in.readerIndex(mark);
            }
        }
        return null;
    }
}
