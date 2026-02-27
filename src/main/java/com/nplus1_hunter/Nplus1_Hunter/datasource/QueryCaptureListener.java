package com.nplus1_hunter.Nplus1_Hunter.datasource;

import com.nplus1_hunter.Nplus1_Hunter.autoconfigure.NPlusOneProperties;
import com.nplus1_hunter.Nplus1_Hunter.context.RequestContext;
import com.nplus1_hunter.Nplus1_Hunter.context.RequestContextHolder;
import com.nplus1_hunter.Nplus1_Hunter.normalization.SqlNormalizer;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nplus1_hunter.Nplus1_Hunter.stacktrace.StackTraceAnalyzer; // Bunu ekle


import java.util.List;
import java.util.Optional;

public class QueryCaptureListener implements QueryExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(QueryCaptureListener.class);
    private final SqlNormalizer normalizer = new SqlNormalizer();
    private final StackTraceAnalyzer stackTraceAnalyzer = new StackTraceAnalyzer();

    private final NPlusOneProperties properties;

    public QueryCaptureListener(NPlusOneProperties properties) {
        this.properties = properties;
    }

//    private static final int N_PLUS_ONE_THRESHOLD = 3;

    @Override
    public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        RequestContext context = RequestContextHolder.get();
        if (context == null) return;

        Optional<String> locationOpt = stackTraceAnalyzer.findTriggerPoint();
        String location = locationOpt.orElse("Unknown Location");

        for (QueryInfo queryInfo : queryInfoList) {
            String originalSql = queryInfo.getQuery();
            String normalizedSql = normalizer.normalize(originalSql);

            String key = normalizedSql + "||" + location;

            context.addQuery(key);

            int count = context.getQueryCounts().get(key);

            if (count > properties.getThreshold()) {
                handleNPlusOne(originalSql, count, location);
            }
        }
    }

    private void handleNPlusOne(String sql, int count, String location) {
        if ("EXCEPTION".equalsIgnoreCase(properties.getErrorLevel())) {
            String errorMessage = String.format("🚨 N+1 PROBLEM DETECTED! (Repetitions: %d) - Location: %s", count, location);
            throw new RuntimeException(errorMessage);
        }

        logger.error("\u001B[31m✘ \u001B[33mN+1 PROBLEM DETECTED (Repetitions: \u001B[31m{}\u001B[33m)\u001B[0m", count);
        logger.error("\u001B[33m📄 Query: {}\u001B[0m", sql);
        logger.error("\u001B[33m📍 Location: \u001B[31m{}\u001B[0m", location);
    }

    @Override
    public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) { }
}