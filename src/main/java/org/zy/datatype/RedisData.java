package org.zy.datatype;

/**
 * <p>
 * Redis数据结构
 * </p>
 *
 * @author ZPY
 * @date 2023/6/8 21:33
 */

public abstract class RedisData {

    protected long timeout;

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
