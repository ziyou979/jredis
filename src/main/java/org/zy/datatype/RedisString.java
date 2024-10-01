package org.zy.datatype;

import lombok.ToString;
import org.zy.exception.JredisException;
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

    public String getRange(long start, long end) {
        int length = str.length();
        // 支持负数索引，负数索引代表从后往前数
        if (start < 0) {
            start += length;
        }
        if (end < 0) {
            end += length;
        }
        // 允许索引超出字符串长度
        if (start >= length || start > end) {
            return "";
        }
        if (end >= length) {
            end = length - 1;
        }
        // 确保 start 和 end 在有效范围内
        start = Math.max(start, 0);
        end = Math.min(end, length - 1);
        return str.substring((int) start, (int) end + 1);
    }

    /* SET key value [NX] [XX] [KEEPTTL] [GET] [EX <seconds>] [PX <milliseconds>]
     *     [EXAT <seconds-timestamp>][PXAT <milliseconds-timestamp>] */
    public void set(String str) {
        // 利用StringBuilder重置的效率比新建对象高
        this.str.setLength(0);
        this.str.append(str);
    }

    public int strLen() {
        return str.length();
    }

    public String incrBy(String val, boolean incr) {
        if (NumberUtil.isNotNumber(val)) {
            throw JredisException.invalidInt();
        }
        BigDecimal num = new BigDecimal(str.toString());
        BigDecimal value = new BigDecimal(val);
        return incr ? num.add(value).toPlainString() : num.subtract(value).toPlainString();
    }

    public String append(String s) {
        return str.append(s).toString();
    }

    public static void main(String[] args) {
        String str = "123456789";
        RedisString redisString = new RedisString(str);
        System.out.println(redisString.getRange(-12, 1));
    }

}