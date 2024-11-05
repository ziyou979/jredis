package org.zy.resp.data;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zy.exception.IncompleteCommandException;
import org.zy.resp.RespType;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * <p>
 * Resp v2协议字符串，二进制安全的字符串，允许换行
 * </p>
 *
 * @author ZPY
 * @date 2023/6/10 20:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkString implements Resp {

    private String content;

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(RespType.BULK_STRINGS.getCode());
        if (Objects.isNull(content)) {
            buffer.writeBytes(RespType.NULL.getCodes());
            buffer.writeBytes(RespType.CRLF.getCodes());
        } else {
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
            buffer.writeBytes(buffer.writeBytes(String.valueOf(bytes.length).getBytes(StandardCharsets.UTF_8)));
            buffer.writeBytes(RespType.CRLF.getCodes());
            buffer.writeBytes(bytes);
            buffer.writeBytes(RespType.CRLF.getCodes());
        }
    }

    @Override
    public Resp decode(ByteBuf buffer) {
        long len = readInteger(buffer);
        // "$-1\r\n"表示Null Bulk String
        if (len == -1L) {
            return new BulkString(null);
        }
        // 批量回复前两个字节标记了批量字符串的长度，例如"$6\r\nfoobar\r\n"，因此可通过前两个字节信息对内容进行校验，除-1外的负数非法
        if (len < 0 || buffer.readableBytes() < len + 2) {
            throw new IncompleteCommandException();
        }
        byte[] bytes = new byte[Math.toIntExact(len)];
        buffer.readBytes(bytes);
        if (buffer.readByte() != RespType.CR.getCode() || buffer.readByte() != RespType.LF.getCode()) {
            throw new IncompleteCommandException();
        }
        return new BulkString(new String(bytes));
    }
}
