package org.zy.datatype;

import lombok.ToString;
import org.zy.util.NumberUtil;

import java.math.BigDecimal;

/**
 * <p>
 * Redis String的底层数据结构，基于Java的StringBuilder实现
 * </p>
 *
 * @author ZPY
 * @date 2023/6/9 14:36
 */
@ToString
public class RedisString extends RedisData {

    private final StringBuilder str;

    public RedisString(String str) {
        this(str, -1L);
    }

    public RedisString(String str, long timeout) {
        this.str = new StringBuilder(str);
        this.timeout = timeout;
    }

    public String get() {
        return str.toString();
    }

    public String getrange(long start, long end) {
        return str.substring((int) start, (int) end + 1);
    }

    /* SET key value [NX] [XX] [KEEPTTL] [GET] [EX <seconds>] [PX <milliseconds>]
     *     [EXAT <seconds-timestamp>][PXAT <milliseconds-timestamp>] */
    public void set(String str) {
        // 利用StringBuilder重置的效率比新建对象高
        this.str.setLength(0);
        this.str.append(str);
    }

    public int strlen() {
        return str.length();
    }

    public String incrBy(String val, boolean incr) {
        if (NumberUtil.isNotNumber(val)) {
            throw new IllegalArgumentException("ERR value is not an integer or out of range");
        }
        BigDecimal num = new BigDecimal(str.toString());
        BigDecimal value = new BigDecimal(val);
        return incr ? num.add(value).toPlainString() : num.subtract(value).toPlainString();
    }

    public String append(String s) {
        return str.append(s).toString();
    }

}