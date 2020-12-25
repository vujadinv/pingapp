package com.company.pingapp.client;

import com.company.pingapp.client.channels.MessageChannelType;
import com.company.pingapp.core.PingAppRunnable;
import com.company.pingapp.configuration.PingConfiguration;
import com.company.pingapp.client.messages.AlphaNumericComposer;
import com.company.pingapp.client.stats.StatManager;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Pitcher/Client class implements PingAppRunnable interface and can be started from the command line.
 *
 * @author      vv
 */
public class Pitcher implements PingAppRunnable {
    private static int DefaultPeriodInMilliseconds = 1000;  // 1 second
    private static int ThreadPoolSize = 32;  // TODO: Should be configurable

    private final ScheduledExecutorService workers;
    private final long rate;
    private final PingConfiguration config;
    private final StatManager statManager;

    public Pitcher(PingConfiguration config) {
        this.workers = Executors.newScheduledThreadPool(ThreadPoolSize);
        this.config = config;

        // We want to schedule each message to be sent via dispatcher in a precomputed fraction of a second.
        this.rate = DefaultPeriodInMilliseconds / config.getMps();

        this.statManager = new StatManager();
    }

    /**
     * Schedules repeatable task for sending messages at specific rate and, in addition, separate task
     * for reporting stats.
     */
    @Override
    public void run() {
        System.out.printf("%s STARTED at: %s%n", this.getClass().getName(), LocalDateTime.now());

        workers.scheduleAtFixedRate(this.statManager, DefaultPeriodInMilliseconds, DefaultPeriodInMilliseconds, TimeUnit.MILLISECONDS);

        var publisher = new MessagePublisher(config, new AlphaNumericComposer(config.getSize()), MessageChannelType.SOCKET, this.statManager);
        workers.scheduleAtFixedRate(() -> workers.execute(publisher), 0, this.rate, TimeUnit.MILLISECONDS);

        System.out.printf("%s Schedule finished%n", this.getClass().getName());
    }

    @Override
    public void shutdown() {
        this.statManager.displayFinalStats();
        System.out.printf("%s SHUTDOWN in progress%n", this.getClass().getName());
        this.workers.shutdown();
        System.out.printf("%s FINISHED%n", this.getClass().getName());
    }

    @Override
    public boolean await() throws InterruptedException {
        return this.workers.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}
