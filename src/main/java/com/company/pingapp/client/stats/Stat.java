package com.company.pingapp.client.stats;

import java.time.LocalDateTime;

public class Stat {
    private final int id;
    private final LocalDateTime recorded;
    private final long duration;
    private final StatType type;
    private final boolean isSuccess;

    public Stat(int id, LocalDateTime recorded, long duration, StatType type, boolean isSuccess) {
        this.id = id;
        this.recorded = recorded;
        this.duration = duration;
        this.type = type;
        this.isSuccess = isSuccess;
    }

    public int getId() {
        return this.id;
    }

    public LocalDateTime getRecorded() {
        return this.recorded;
    }

    public long getDuration() {
        return this.duration;
    }

    public StatType getType() {
        return this.type;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

}
