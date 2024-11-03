package org.zy.util;

public class TraceUtil {

    private static final ThreadLocal<String> TRACE_ID = new ThreadLocal<>();

    public static String getTraceId() {
        return ObjectUtil.getOrDefault(TRACE_ID.get(), TraceUtil::newTraceId);
    }

    public static String newTraceId() {
        String traceId = IdUtil.simpleUUID();
        TRACE_ID.set(traceId);
        return traceId;
    }

    public static void bind(String traceId) {
        TRACE_ID.set(traceId);
    }

}
