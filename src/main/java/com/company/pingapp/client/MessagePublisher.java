package com.company.pingapp.client;

import com.company.pingapp.client.channels.MessageChannelFactory;
import com.company.pingapp.client.channels.MessageChannelType;
import com.company.pingapp.client.messages.MessageComposer;
import com.company.pingapp.client.stats.Stat;
import com.company.pingapp.client.stats.StatManager;
import com.company.pingapp.client.stats.StatType;
import com.company.pingapp.configuration.PingConfiguration;
import org.apache.commons.lang3.time.StopWatch;

import java.time.LocalDateTime;

/**
 * Publishes messages at scheduled intervals. Class is responsible for creating messages,
 * sending/receiving, timing, and reporting stats.
 *
 * @author      vv
 */
public class MessagePublisher implements Runnable {

    private final PingConfiguration config;
    private final MessageComposer composer;
    private final MessageChannelType channelType;
    private final StatManager stats;

    public MessagePublisher(PingConfiguration config, MessageComposer composer, MessageChannelType channelType, StatManager stats) {
        this.config = config;
        this.composer = composer;
        this.channelType = channelType;
        this.stats = stats;
    }

    @Override
    public void run() {
        try {
            doRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doRun() {
        var message = composer.getMessage();
        var stopWatch = new StopWatch();

        System.out.printf("%s (%s) invoked at %s. Msg (%s): %s%n",
                this.getClass().getName(),
                Thread.currentThread().getId(),
                LocalDateTime.now(),
                message.getId(),
                message.getMessage()
        );

        // Send message and update stats
        stopWatch.start();
        var channel = MessageChannelFactory.create(this.channelType, this.config);

        var sendSuccess = channel.send(message.getMessage());

        stopWatch.stop();
        var sendingDuration = stopWatch.getTime();
        this.stats.update(new Stat(message.getId(), LocalDateTime.now(), sendingDuration, StatType.SENT, sendSuccess));

        if (!sendSuccess) {
            channel.close();
            return;
        }

        // Receive response and update stats
        stopWatch.reset();
        stopWatch.start();
        var response = channel.receive();
        stopWatch.stop();
        var responseDuration = stopWatch.getTime();
        var responseSuccess = message.getMessage().equals(response);

        this.stats.update(new Stat(message.getId(), LocalDateTime.now(), responseDuration, StatType.RECEIVED, responseSuccess));
        this.stats.update(new Stat(message.getId(), LocalDateTime.now(), sendingDuration + responseDuration, StatType.ROUND_TRIP, (sendSuccess && responseSuccess)));

        // Close channel
        channel.close();
    }



}
