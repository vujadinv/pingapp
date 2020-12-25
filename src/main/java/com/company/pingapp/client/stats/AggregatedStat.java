package com.company.pingapp.client.stats;

public class AggregatedStat {
    private final int total;
    private final long min;
    private final long max;
    private final double avg;
    private final StatType type;

    public AggregatedStat(int total, long min, long max, double avg, StatType type) {
        this.total = total;
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.type = type;
    }

    public int getTotal() {
        return this.total;
    }

    public long getMin() {
        return this.min;
    }

    public long getMax() {
        return this.max;
    }

    public double getAvg() {
        return this.avg;
    }

    public StatType getType() {
        return this.type;
    }

}
