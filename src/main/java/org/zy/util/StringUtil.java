package org.zy.util;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 字符串工具类
 *
 * @author ZPY
 * @date 2024/9/24
 */
@Slf4j
public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return Objects.isNull(str) || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        return Objects.isNull(str) || str.isBlank();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 格式化字符串，占位符为{}
     *
     * @param templateStr 模板字符串
     * @param args        参数
     * @return String
     */
    public static String format(String templateStr, Object... args) {
        if (ArrayUtil.isEmpty(args)) {
            return templateStr;
        }

        int i = 0;
        StringBuilder strBuf = new StringBuilder(templateStr.length() + 50);

        for (int L = 0; L < args.length; ++L) {
            int j = templateStr.indexOf("{}", i);
            if (j == -1) {
                return templateStr;
            }

            if (isEscapedDelimiter(templateStr, j)) {
                if (!isDoubleEscaped(templateStr, j)) {
                    --L;
                    strBuf.append(templateStr, i, j - 1);
                    strBuf.append('{');
                    i = j + 1;
                } else {
                    strBuf.append(templateStr, i, j - 1);
                    deeplyAppendParameter(strBuf, args[L], new HashMap<>());
                    i = j + 2;
                }
            } else {
                strBuf.append(templateStr, i, j);
                deeplyAppendParameter(strBuf, args[L], new HashMap<>());
                i = j + 2;
            }
        }

        strBuf.append(templateStr, i, templateStr.length());
        return strBuf.toString();
    }

    static boolean isEscapedDelimiter(String messagePattern, int delimiterStartIndex) {
        if (delimiterStartIndex == 0) {
            return false;
        } else {
            char potentialEscape = messagePattern.charAt(delimiterStartIndex - 1);
            return potentialEscape == '\\';
        }
    }

    static boolean isDoubleEscaped(String messagePattern, int delimiterStartIndex) {
        return delimiterStartIndex >= 2 && messagePattern.charAt(delimiterStartIndex - 2) == '\\';
    }

    static void deeplyAppendParameter(StringBuilder strBuf, Object o, Map<Object[], Object> seenMap) {
        if (Objects.isNull(o)) {
            strBuf.append("null");
        } else {
            if (!o.getClass().isArray()) {
                safeObjectAppend(strBuf, o);
            } else {
                switch (o) {
                    case boolean[] booleanArray -> ArrayUtil.booleanArrayAppend(strBuf, booleanArray);
                    case byte[] byteArray -> ArrayUtil.byteArrayAppend(strBuf, byteArray);
                    case char[] charArray -> ArrayUtil.charArrayAppend(strBuf, charArray);
                    case short[] shortArray -> ArrayUtil.shortArrayAppend(strBuf, shortArray);
                    case int[] intArray -> ArrayUtil.intArrayAppend(strBuf, intArray);
                    case long[] longArray -> ArrayUtil.longArrayAppend(strBuf, longArray);
                    case float[] floatArray -> ArrayUtil.floatArrayAppend(strBuf, floatArray);
                    case double[] doubleArray -> ArrayUtil.doubleArrayAppend(strBuf, doubleArray);
                    default -> ArrayUtil.objectArrayAppend(strBuf, (Object[]) o, seenMap);
                }
            }
        }
    }

    private static void safeObjectAppend(StringBuilder strBuf, Object o) {
        String oAsString = String.valueOf(o);
        strBuf.append(oAsString);
    }


    public static void main(String[] args) {
        String template = "hello {}, begin at {} ";
        System.out.println(format(template, 4, System.currentTimeMillis()));

    }
}
