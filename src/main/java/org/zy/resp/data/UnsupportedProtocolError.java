package org.zy.resp.data;

import io.netty.buffer.ByteBuf;

/**
 * <p>
 * 检测到不符合Resp协议规范时的兜底处理
 * </p>
 *
 * @author ZPY
 * @date 2023/6/11 16:25
 */
public class UnsupportedProtocolError extends Error {

    public UnsupportedProtocolError() {
        super("Unsupported Protocol");
    }

    @Override
    public Resp decode(ByteBuf buffer) {
        readLine(buffer); // 未知协议，将\r\n前的数据进行读取后丢弃
        return this;
    }
}
