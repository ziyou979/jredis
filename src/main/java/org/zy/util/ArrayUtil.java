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

    public static <T> T getFirst(T[] array) {
        if (isEmpty(array)) {
            return null;
        }
        return array[0];
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

    static void booleanArrayAppend(StringBuilder strBuf, boolean[] array) {
        strBuf.append('[');
        int len = array.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(array[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void byteArrayAppend(StringBuilder strBuf, byte[] array) {
        strBuf.append('[');
        int len = array.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(array[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void charArrayAppend(StringBuilder strBuf, char[] array) {
        strBuf.append('[');
        int len = array.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(array[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void shortArrayAppend(StringBuilder strBuf, short[] array) {
        strBuf.append('[');
        int len = array.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(array[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void intArrayAppend(StringBuilder strBuf, int[] array) {
        strBuf.append('[');
        int len = array.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(array[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void longArrayAppend(StringBuilder strBuf, long[] array) {
        strBuf.append('[');
        int len = array.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(array[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void floatArrayAppend(StringBuilder strBuf, float[] array) {
        strBuf.append('[');
        int len = array.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(array[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }

    static void doubleArrayAppend(StringBuilder strBuf, double[] array) {
        strBuf.append('[');
        int len = array.length;

        for (int i = 0; i < len; ++i) {
            strBuf.append(array[i]);
            if (i != len - 1) {
                strBuf.append(", ");
            }
        }

        strBuf.append(']');
    }
}
