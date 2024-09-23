package org.zy.datatype;

/**
 * <p>
 * Redis数据结构
 * </p>
 *
 * @author ZPY
 * @date 2023/6/8 21:33
 */
public interface RedisData {

    long getTimeout();

    void setTimeout(long timeout);
}
