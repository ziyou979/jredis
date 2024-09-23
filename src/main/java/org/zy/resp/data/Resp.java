package org.zy.resp.data;

import io.netty.buffer.ByteBuf;
import org.zy.exception.IncompleteCommandException;
import org.zy.resp.RespType;

/**
 * <p>
 * Resp协议通用接口
 * </p>
 *
 * @author ZPY
 * @date 2023/6/9 15:41
 */
public interface Resp {
    /**
     * 将实体类内容编码为Resp协议格式
     *
     * @param buffer 字节缓冲流
     */
    void encode(ByteBuf buffer);

    /**
     * 将Resp协议格式的内容编码为实体类
     *
     * @param buffer 字节缓冲流
     * @return 对应实体类
     */
    Resp decode(ByteBuf buffer);

    default long readInteger(ByteBuf buffer) {
        String num = readLine(buffer);
        return Long.parseLong(num);
    }

    /**
     * 第一个字符被用于判断类型，已事先被使用
     *
     * @param buffer 缓冲
     * @return 标志位后对应的字符串
     */
    default String readLine(ByteBuf buffer) {
        char b;
        StringBuilder sb = new StringBuilder();
        while (buffer.readableBytes() > 0 && (b = (char) buffer.readByte()) != RespType.CR.getCode()) {
            sb.append(b);
        }
        if (buffer.readableBytes() == 0 || buffer.readByte() != RespType.LF.getCode()) {
            throw new IncompleteCommandException();
        }
        return sb.toString();
    }
}
