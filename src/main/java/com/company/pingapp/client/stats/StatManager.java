package com.company.pingapp.client.stats;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Manages storage of reported stats, general statistics and produces reports based on those stats.
 *
 * @author      vv
 */
public class StatManager implements Runnable {
    private static final int MaxCapacity = 10000;  // TODO: Should be configurable
    private final AtomicInteger totalNumberOfSentMessages;
    private final AtomicInteger totalNumberOfReceivedMessages;
    private final Queue<Stat> statQueue;

    public StatManager() {
        this.statQueue = new ConcurrentLinkedQueue<Stat>();
        this.totalNumberOfSentMessages = new AtomicInteger(0);
        this.totalNumberOfReceivedMessages = new AtomicInteger(0);
    }

    @Override
    public void run() {
        var now = LocalDateTime.now();

        System.out.printf("%s (%s) invoked: %s%n",
                this.getClass().getName(),
                Thread.currentThread().getId(),
                now
        );

        try {
            this.displayStats(now);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Stat stat) {

        if (stat.getType() == StatType.SENT)
            this.totalNumberOfSentMessages.addAndGet(1);

        if (stat.getType() == StatType.RECEIVED)
            this.totalNumberOfReceivedMessages.addAndGet(1);

        if (this.statQueue.size() > MaxCapacity)
            this.statQueue.remove();

        this.statQueue.add(stat);
    }

    public void displayStats(LocalDateTime now) {
        var from = now.minusSeconds(1);

        List<Stat> currentStats = this.statQueue.stream().filter(s -> s.getRecorded().isAfter(from)).collect(Collectors.toList());

        var sentLiveStat = this.getLiveStat(currentStats, StatType.SENT);
        var receivedLiveStat = this.getLiveStat(currentStats, StatType.RECEIVED);
        var roundTripLiveStat = this.getLiveStat(currentStats, StatType.ROUND_TRIP);

        System.out.printf("    Total Sent: %s; Received: %s%n",
                this.totalNumberOfSentMessages,
                this.totalNumberOfReceivedMessages
        );

        this.displayLiveStat(sentLiveStat);
        this.displayLiveStat(receivedLiveStat);
        this.displayLiveStat(roundTripLiveStat);

    }

    public void displayFinalStats() {

        var sentStats = this.statQueue.stream().filter(s -> {
            return s.getType() == StatType.SENT && s.isSuccess();
        }).collect(Collectors.toList());

        var receivedStats = this.statQueue.stream().filter(s -> {
            return s.getType() == StatType.RECEIVED && s.isSuccess();
        }).collect(Collectors.toList());

        System.out.printf("%s (%s) FINAL STATS:%n",
                this.getClass().getName(),
                Thread.currentThread().getId()
        );

        System.out.printf("    Total Sent/success: %s/%s; Received/success: %s/%s%n",
                this.totalNumberOfSentMessages,
                sentStats.size(),
                this.totalNumberOfReceivedMessages,
                receivedStats.size()
        );

    }

    public int getSentTotal() {
        return this.totalNumberOfSentMessages.intValue();
    }

    public int getReceivedTotal() {
        return this.totalNumberOfReceivedMessages.intValue();
    }

    public long getSuccessfulTotal() {
        return this.statQueue.stream().filter(s -> {
            return s.getType() == StatType.ROUND_TRIP && s.isSuccess();
        }).count();
    }

    private void displayLiveStat(AggregatedStat stat) {

        System.out.printf("    %s/1s: Total: %s; Max: %s ms; Min: %s ms; Avg: %.0f ms%n",
                stat.getType(),
                stat.getTotal(),
                stat.getMax(),
                stat.getMin(),
                stat.getAvg()
        );

    }

    private AggregatedStat getLiveStat(List<Stat> currentStats, StatType type) {
        var stats = currentStats.stream().filter(s -> s.getType() == type).collect(Collectors.toList());

        var total = stats.size();
        var max = stats.stream().mapToLong(s -> s.getDuration()).max().orElse(0L);
        var min = stats.stream().mapToLong(s -> s.getDuration()).min().orElse(0L);
        var avg = stats.stream().mapToLong(s -> s.getDuration()).average().orElse(0.0d);

        return new AggregatedStat(total, min, max, avg, type);
    }
}