package com.nplus1_hunter.Nplus1_Hunter.normalization;

import java.util.regex.Pattern;

public class SqlNormalizer {

    private static final Pattern STRING_PATTERN = Pattern.compile("'[^']*'");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\b\\d+\\b");

    public String normalize(String sql) {
        if (sql == null) return "";

        String noStrings = STRING_PATTERN.matcher(sql).replaceAll("?");

        return NUMBER_PATTERN.matcher(noStrings).replaceAll("?");
    }
}