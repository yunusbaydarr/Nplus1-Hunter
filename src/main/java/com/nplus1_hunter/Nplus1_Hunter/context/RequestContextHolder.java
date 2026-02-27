package com.nplus1_hunter.Nplus1_Hunter.context;

public class RequestContextHolder {
    private static final ThreadLocal<RequestContext> CONTEXT = ThreadLocal.withInitial(RequestContext::new);

    public static RequestContext get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}