package com.company.pingapp.client.channels;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DummyMessageChannelTest {

    @Test
    void channelOperations() {
        var message = "test";

        var cmc = new DummyMessageChannel();

        assertTrue(cmc.send(message));
        assertEquals(message, cmc.receive());

    }
}