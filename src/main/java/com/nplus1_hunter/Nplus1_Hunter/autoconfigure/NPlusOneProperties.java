package com.nplus1_hunter.Nplus1_Hunter.autoconfigure;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nplus1")
public class NPlusOneProperties {


    private boolean enabled = true;

    private int threshold = 3;

    private String errorLevel = "LOG";

    private int logInterval = 1;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(String errorLevel) {
        this.errorLevel = errorLevel;
    }

    public int getLogInterval() {
        return logInterval;
    }

    public void setLogInterval(int logInterval) {
        this.logInterval = logInterval;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
