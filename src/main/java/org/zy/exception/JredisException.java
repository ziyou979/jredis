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
}

