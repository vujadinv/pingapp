package com.company.pingapp.client;

import com.company.pingapp.client.messages.AlphaNumericComposer;
import com.company.pingapp.client.channels.MessageChannelType;
import com.company.pingapp.client.stats.StatManager;
import com.company.pingapp.configuration.PingConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessagePublisherTest {

    @Test
    void run() {

        var config = new PingConfiguration();
        var sm = new StatManager();
        var composer = new AlphaNumericComposer(1);

        var mp = new MessagePublisher(config, composer, MessageChannelType.CONSOLE, sm);

        mp.run();

        assertEquals(sm.getSentTotal(), 1);
        assertEquals(sm.getReceivedTotal(), 1);
        assertEquals(sm.getSuccessfulTotal(), 1);
    }
}