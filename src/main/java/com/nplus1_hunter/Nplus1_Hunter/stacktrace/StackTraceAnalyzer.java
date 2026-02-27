package com.nplus1_hunter.Nplus1_Hunter.stacktrace;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class StackTraceAnalyzer {

    private static final List<String> IGNORED_PACKAGES = List.of(
            "java.",
            "javax.",
            "sun.",
            "jdk.",
            "org.springframework.",
            "org.hibernate.",
            "com.zaxxer.",
            "net.ttddyy.",
            "org.apache.",
            "com.nplus1_hunter.",
            "org.junit.",
            "org.mockito."
    );

    public Optional<String> findTriggerPoint() {
        return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk(this::findFirstUserFrame);
    }

    private Optional<String> findFirstUserFrame(Stream<StackWalker.StackFrame> frames) {
        return frames
                .filter(frame -> IGNORED_PACKAGES.stream().noneMatch(prefix -> frame.getClassName().startsWith(prefix)))

                .filter(frame -> !frame.getClassName().contains("$Proxy"))
                .filter(frame -> !frame.getClassName().contains("$$"))

                .findFirst()
                .map(frame -> frame.getClassName() + "." + frame.getMethodName() +
                        " (" + frame.getFileName() + ":" + frame.getLineNumber() + ")");
    }
}