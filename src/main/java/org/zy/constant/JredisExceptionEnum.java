package org.zy.constant;

import org.zy.util.StringUtil;

/**
 * 异常码
 *
 * @author ZPY
 * @date 2024/9/25
 */
public enum JredisExceptionEnum {
    INVALID_COMMAND("ERR unknown command '{}'"),
    INVALID_ARGUMENT("ERR wrong number of arguments for '{}' command"),
    INVALID_TYPE("WRONGTYPE Operation against a key holding the wrong kind of value"),
    INVALID_INT("ERR value is not an integer or out of range"),
    INVALID_TIMEOUT("ERR invalid expire time in setTimeout"),
    INVALID_INDEX("ERR index out of range"),
    INVALID_LENGTH("ERR invalid length"),
    INVALID_VALUE("ERR invalid value"),
    INVALID_VALUE_TYPE("ERR invalid value type"),
    INVALID_VALUE_LENGTH("ERR invalid value length");


    private final String reason;

    JredisExceptionEnum(String reason) {
        this.reason = reason;
    }

    public String getReason(Object... args) {
        return StringUtil.format(reason, args);
    }

}
