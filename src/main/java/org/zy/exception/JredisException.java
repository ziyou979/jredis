package org.zy.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zy.constant.JredisExceptionEnum;

/**
 * redis异常
 *
 * @author ZPY
 * @date 2024/9/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JredisException extends RuntimeException {

    private int code;

    private String reason;

    public JredisException(int code, String reason) {
        super(reason);
        this.code = code;
        this.reason = reason;
    }

    public JredisException(int code, String reason, Throwable cause) {
        super(reason, cause);
        this.code = code;
        this.reason = reason;
    }

    public static JredisException invalidCommand(String command) {
        return new JredisException(1001, JredisExceptionEnum.INVALID_COMMAND.getReason(command));
    }

    public static JredisException invalidArgument(String command) {
        return new JredisException(1002, JredisExceptionEnum.INVALID_ARGUMENT.getReason(command));
    }

    public static JredisException invalidType() {
        return new JredisException(1003, JredisExceptionEnum.INVALID_TYPE.getReason());
    }

    public static JredisException invalidInt() {
        return new JredisException(1004, JredisExceptionEnum.INVALID_INT.getReason());
    }

    public static JredisException invalidTimeout() {
        return new JredisException(1005, JredisExceptionEnum.INVALID_TIMEOUT.getReason());
    }

    public static JredisException invalidIndex() {
        return new JredisException(1006, JredisExceptionEnum.INVALID_INDEX.getReason());
    }


    public static JredisException invalidLength() {
        return new JredisException(1007, JredisExceptionEnum.INVALID_LENGTH.getReason());
    }

    public static JredisException invalidValue() {
        return new JredisException(1008, JredisExceptionEnum.INVALID_VALUE.getReason());
    }

    public static JredisException invalidValueType() {
        return new JredisException(1009, JredisExceptionEnum.INVALID_VALUE_TYPE.getReason());
    }

    public static JredisException invalidValueLength() {
        return new JredisException(1010, JredisExceptionEnum.INVALID_VALUE_LENGTH.getReason());
    }

    public static JredisException invalidSyntax() {
        return new JredisException(1011, JredisExceptionEnum.INVALID_SYNTAX.getReason());
    }
}

