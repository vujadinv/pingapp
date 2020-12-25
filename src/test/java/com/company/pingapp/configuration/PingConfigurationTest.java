package com.company.pingapp.configuration;

import com.company.pingapp.core.PingRunMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PingConfigurationTest {

    @Test
    void configuration() {

        String size = "50";
        String mps = "10";
        String host = "localhost";
        String bind = "127.0.0.1";
        String port = "10001";

        var config = new PingConfiguration(true, false, size, mps, host, bind, port);

        assertEquals(config.getSize(), 50);
        assertEquals(config.getMps(), 10);
        assertEquals(config.getHostname(), host);
        assertEquals(config.getBindAddress(), bind);
        assertEquals(config.getPort(), 10001);
        assertSame(config.getRunMode(), PingRunMode.PITCHER);

    }
}