package ru.alexeymalinov.taskautomation.core.model;

public enum TimeIntervalType {
    SECONDS(1000),MINUTES(60000),HOURS(3600000),DAYS(86400);

    private final int factor;

    TimeIntervalType(int factor) {
        this.factor = factor;
    }

    public int getFactor() {
        return factor;
    }
}
