package com.company.pingapp.client.channels;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Dummy MessageChannel implementation emulating Sockets usage.
 *
 * @author vv
 */
public class DummyMessageChannel implements MessageChannel {
    private String message;

    public DummyMessageChannel() {
    }

    public boolean send(String message) {
        this.message = message;

        emulateDelay();

        return true;
    }

    public String receive() {
        emulateDelay();

        return this.message;
    }

    private void emulateDelay() {
        // Emulate delay. Sleep in range [0, 1000) ms.
        try {
            long sleep = ThreadLocalRandom.current().nextLong(0L, 1000L);
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
    }
}

