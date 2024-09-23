package org.zy.exception;

/**
 * <p>
 *
 * </p>
 *
 * @author ZPY
 * @date 2023/6/11 10:08
 */
public class IncompleteCommandException extends RuntimeException {

    public IncompleteCommandException() {
        super("非法命令，请检查命令是否完整");
    }

    public IncompleteCommandException(String message) {
        super(message);
    }
}
