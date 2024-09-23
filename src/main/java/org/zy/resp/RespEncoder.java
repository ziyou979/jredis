package org.zy.resp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.zy.resp.data.Resp;

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
            msg.encode(out);
        } catch (Exception e) {
            ctx.close();
            log.error("数据类类编码时发生异常：{}", e.getMessage());
        }
    }
}
