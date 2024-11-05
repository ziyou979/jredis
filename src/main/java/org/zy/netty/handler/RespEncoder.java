package org.zy.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.zy.resp.RespType;
import org.zy.resp.data.Resp;
import org.zy.util.TraceUtil;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * <p>
 * Resp编码器，将实体类转换为Resp协议格式的字节内容并输出
 * </p>
 *
 * @author ZPY
 * @date 2023/6/13 10:05
 */
@Slf4j
public class RespEncoder extends MessageToByteEncoder<Resp> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Resp msg, ByteBuf out) {
        try {
            log.debug("encode 编码数据：{}，traceId：{}", msg, TraceUtil.getTraceId());
            if (Objects.isNull(msg)) {
                out.writeBytes(RespType.NULL.getCodes());
                out.writeBytes(RespType.CRLF.getCodes());
            } else {
                msg.encode(out);
            }
            log.debug("encode 编码数据完成：{}，traceId：{}", out.toString(StandardCharsets.UTF_8), TraceUtil.getTraceId());
        } catch (Exception e) {
            ctx.close();
            log.error("数据类编码时发生异常：{}，traceId：{}", e.getMessage(), TraceUtil.getTraceId());
        }
    }
}
