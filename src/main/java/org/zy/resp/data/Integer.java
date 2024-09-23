package org.zy.resp.data;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zy.resp.RespType;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * Resp v2协议整数，64位有符号整数
 * </p>
 *
 * @author ZPY
 * @date 2023/6/10 17:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Integer implements Resp {

    private long content;

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(RespType.INTEGER.getCode());
        buffer.writeBytes(String.valueOf(content).getBytes(StandardCharsets.UTF_8));
        buffer.writeBytes(RespType.CRLF.getCodes());
    }

    @Override
    public Resp decode(ByteBuf buffer) {
        setContent(readInteger(buffer));
        return this;
    }
}
