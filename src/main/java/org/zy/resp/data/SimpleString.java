package org.zy.resp.data;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zy.resp.RespType;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * Resp v2协议简单字符串，用于返回简单结果，非二进制安全且不允许换行
 * </p>
 *
 * @author ZPY
 * @date 2023/6/9 16:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleString implements Resp {
    private String content;

    public static final SimpleString OK = new SimpleString("OK");

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(RespType.SIMPLE_STRING.getCode());
        buffer.writeBytes(getContent().getBytes(StandardCharsets.UTF_8));
        buffer.writeBytes(RespType.CRLF.getCodes());
    }

    @Override
    public Resp decode(ByteBuf buffer) {
        setContent(readLine(buffer));
        return this;
    }
}
