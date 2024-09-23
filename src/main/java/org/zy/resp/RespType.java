package org.zy.resp;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * Resp协议中规定的特殊符号枚举类
 * </p>
 *
 * @author ZPY
 * @date 2023/6/9 15:58
 */
public enum RespType {
    SIMPLE_STRING((byte) '+'),
    BULK_STRINGS((byte) '$'),
    ERROR((byte) '-'),
    INTEGER((byte) ':'),
    ARRAY((byte) '*'),
    CR((byte) '\r'),
    LF((byte) '\n'),
    CRLF("\r\n"),
    NULL("-1");

    private final byte[] code;

    RespType(String code) {
        this.code = code.getBytes(StandardCharsets.UTF_8);
    }

    RespType(byte code) {
        this.code = new byte[]{code};
    }

    public byte[] getCodes() {
        return code;
    }

    public byte getCode() {
        return code[0];
    }
}
