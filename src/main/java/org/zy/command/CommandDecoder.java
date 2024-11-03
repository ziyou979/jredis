package org.zy.command;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.zy.exception.JredisException;
import org.zy.factory.CommandFactory;
import org.zy.resp.RespDecoderFactory;
import org.zy.resp.data.Array;
import org.zy.resp.data.Resp;
import org.zy.resp.data.SimpleString;
import org.zy.util.TraceUtil;

import java.util.Objects;

@Slf4j
public class CommandDecoder extends LengthFieldBasedFrameDecoder {

    public CommandDecoder() {
        super(Integer.MAX_VALUE, 0, 4);
    }

    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 新的指令到来，生成新的 traceId
        TraceUtil.newTraceId();
        while (in.readableBytes() != 0) {
            int mark = in.readerIndex();
            try {
                Resp resp = RespDecoderFactory.decode(in);
                RedisCommand command;
                switch (resp) {
                    case SimpleString simpleString -> command = CommandFactory.valueOf(simpleString);
                    case Array array -> command = CommandFactory.valueOf(array);
                    default -> throw JredisException.invalidCommand("");
                }
                if (Objects.isNull(command)) {
                    // 不支持的命令，直接返回错误
                    ctx.writeAndFlush(new org.zy.resp.data.Error("unsupported command null"));
                }
            } catch (Exception e) {
                log.error("CommandDecoder.decode 解析指令时发生异常，traceId: {}", TraceUtil.getTraceId(), e);
                in.readerIndex(mark);
                break;
            }
        }
        return null;
    }
}
