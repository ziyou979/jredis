package org.zy.resp.data;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error implements Resp {
    public final static Error UNSUPPORTED_PROTOCOL = new Error("Unsupported Protocol");

    private String content;

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(RespType.ERROR.getCode());
        log.error("异常响应{}", content);
        buffer.writeBytes(content.getBytes(StandardCharsets.UTF_8));
        buffer.writeBytes(RespType.CRLF.getCodes());

    }

    @Override
    public Resp decode(ByteBuf buffer) {
        setContent(readLine(buffer));
        return this;
    }
}
