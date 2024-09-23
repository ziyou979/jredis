package org.zy.resp.data;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zy.resp.RespDecoderFactory;
import org.zy.resp.RespType;

/**
 * <p>
 * Resp v2协议数组
 * </p>
 *
 * @author ZPY
 * @date 2023/6/10 20:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Array implements Resp {

    private Resp[] content;

    @Override
    public void encode(ByteBuf buffer) {
        int len = content.length;
        buffer.writeByte(len);
        buffer.writeBytes(RespType.CRLF.getCodes());
        for (Resp resp : content) {
            resp.encode(buffer);
        }
    }

    @Override
    public Resp decode(ByteBuf buffer) {
        long len = readInteger(buffer);
        Resp[] resp = new Resp[Math.toIntExact(len)];
        for (int i = 0; i < len; i++) {
            resp[i] = RespDecoderFactory.decode(buffer);
        }
        setContent(resp);
        return this;
    }
}
