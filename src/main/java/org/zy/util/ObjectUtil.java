package org.zy.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Supplier;

@Slf4j
public class ObjectUtil {

    public static <T> T getOrDefault(T t, T defaultValue) {
        return Objects.nonNull(t) ? t : defaultValue;
    }

    public static <T> T getOrDefault(T t, Supplier<T> defaultValue) {
        return Objects.nonNull(t) ? t : defaultValue.get();
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        try {
            return (T) obj;
        } catch (ClassCastException e) {
            log.error("ObjectUtil.cast 类型转换异常", e);
            return null;
        }
    }

}
