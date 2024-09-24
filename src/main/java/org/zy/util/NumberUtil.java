package org.zy.util;

/**
 * 数字工具类，包含简单的运算
 *
 * @author ZPY
 * @date 2024/9/24
 */
public class NumberUtil {

    public static boolean isNumber(String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        // 遍历每个字符，检查是否为数字
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotNumber(String str) {
        return !isNumber(str);
    }

}
