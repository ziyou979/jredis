package org.zy.resp.data;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zy.resp.RespType;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * Resp v2协议错误信息，用于返回简单的错误信息，非二进制安全且不允许换行
 * </p>
 *
 * @author ZPY
 * @date 2023/6/10 16:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error implements Resp {

    private String content;

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(RespType.ERROR.getCode());
        buffer.writeBytes(content.getBytes(StandardCharsets.UTF_8));
        buffer.writeBytes(RespType.CRLF.getCodes());

    }

    @Override
    public Resp decode(ByteBuf buffer) {
        setContent(readLine(buffer));
        return this;
    }
}
