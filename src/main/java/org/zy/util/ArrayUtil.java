package org.zy.util;

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
}
