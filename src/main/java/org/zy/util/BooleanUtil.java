package org.zy.util;

import java.util.Set;

public class BooleanUtil {
    private static final Set<String> trueValues = Set.of("true", "t", "yes", "y", "1");

    private static final Set<String> falseValues = Set.of("false", "f", "no", "n", "0");


    public static boolean parseBoolean(String booleanStr) {
        if (StringUtil.isEmpty(booleanStr)) {
            return false;
        } else {
            String value = booleanStr.trim().toLowerCase();
            return trueValues.contains(value) || !falseValues.contains(value) && isTrue(value);
        }
    }

    private static boolean isTrue(String str) {
        try {
            return Integer.parseInt(str) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isTrue(Boolean bool) {
        return Boolean.TRUE.equals(bool);
    }

    public static boolean isNotTrue(Boolean bool) {
        return !isTrue(bool);
    }

    public static boolean isFalse(Boolean bool) {
        return Boolean.FALSE.equals(bool);
    }

    public static boolean isNotFalse(Boolean bool) {
        return !isFalse(bool);
    }
}
