package org.zy.util;

import org.zy.constant.CommonConstant;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class IdUtil {

    /**
     * 获取字符串格式UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取不带"-"得到字符串格式UUID
     */
    public static String simpleUUID() {
        return SimpleUUID.simpleUUID();
    }


    public static class SimpleUUID {

        /**
         * 生成uuid，采用 jdk 9 的形式，优化性能
         * copy from mica：<a href="https://github.com/lets-mica/mica/blob/master/mica-core/src/main/java/net/dreamlu/mica/core/utils/StringUtil.java#L335">...</a>
         * <p>
         * 关于mica uuid生成方式的压测结果，可以参考：<a href="https://github.com/lets-mica/mica-jmh/wiki/uuid">mica</a>
         *
         * @return UUID
         */
        public static String simpleUUID() {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            long lsb = random.nextLong();
            long msb = random.nextLong();
            byte[] buf = new byte[32];
            formatUnsignedLong(lsb, buf, 20, 12);
            formatUnsignedLong(lsb >>> 48, buf, 16, 4);
            formatUnsignedLong(msb, buf, 12, 4);
            formatUnsignedLong(msb >>> 16, buf, 8, 4);
            formatUnsignedLong(msb >>> 32, buf, 0, 8);
            return new String(buf, StandardCharsets.UTF_8);
        }

        /**
         * copy from mica：<a href="https://github.com/lets-mica/mica/blob/master/mica-core/src/main/java/net/dreamlu/mica/core/utils/StringUtil.java#L348">mica</a>
         */
        private static void formatUnsignedLong(long val, byte[] buf, int offset, int len) {
            int charPos = offset + len;
            int radix = 1 << 4;
            int mask = radix - 1;
            do {
                buf[--charPos] = CommonConstant.DIGITS[((int) val) & mask];
                val >>>= 4;
            } while (charPos > offset);
        }
    }
}
