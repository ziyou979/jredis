package org.zy.util;

import java.util.Map;
import java.util.Objects;

/**
 * 数组工具类
 *
 * @author ZPY
 * @date 2024/9/24
 */
public class ArrayUtil {

    /**
     * 数组是否为空
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return Objects.isNull(array) || array.length == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return Objects.isNull(array) || array.length == 0;
    }

    static void objectArrayAppend(StringBuilder strBuf, Object[] a, Map<Object[], Object> seenMap) {
        strBuf.append('[');
        if (!seenMap.containsKey(a)) {
            seenMap.put(a, null);
            int len = a.length;

            for (int i = 0; i < len; ++i) {
                StringUtil.deeplyAppendParameter(strBuf, a[i], seenMap);
                if (i != len - 1) {
                    strBuf.append(", ");
                }
            }

            seenMap.remove(a);
        } else {
            strBuf.append("...");
        }

        strBuf.append(']');
    }

    static void booleanArrayAppend(StringBuilder strBuf, boolean[] a) {
        strBuf.append('[');
        int len = a.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(a[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void byteArrayAppend(StringBuilder strBuf, byte[] a) {
        strBuf.append('[');
        int len = a.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(a[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void charArrayAppend(StringBuilder strBuf, char[] a) {
        strBuf.append('[');
        int len = a.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(a[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void shortArrayAppend(StringBuilder strBuf, short[] a) {
        strBuf.append('[');
        int len = a.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(a[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void intArrayAppend(StringBuilder strBuf, int[] a) {
        strBuf.append('[');
        int len = a.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(a[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void longArrayAppend(StringBuilder strBuf, long[] a) {
        strBuf.append('[');
        int len = a.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(a[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void floatArrayAppend(StringBuilder strBuf, float[] a) {
        strBuf.append('[');
        int len = a.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(a[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void doubleArrayAppend(StringBuilder strBuf, double[] a) {
        strBuf.append('[');
        int len = a.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(a[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }
}
