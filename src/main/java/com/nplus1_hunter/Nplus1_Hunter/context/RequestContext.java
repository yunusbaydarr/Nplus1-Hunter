package com.nplus1_hunter.Nplus1_Hunter.context;

import java.util.HashMap;
import java.util.Map;

public class RequestContext {

    private final Map<String, Integer> queryCounts = new HashMap<>();

    public void addQuery(String sql) {
        queryCounts.merge(sql, 1, Integer::sum);
    }

    public Map<String, Integer> getQueryCounts() {
        return queryCounts;
    }
}