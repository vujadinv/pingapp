package com.company.pingapp.client.messages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlphaNumericComposerTest {

    @Test
    void getMessage() {

        var composer = new AlphaNumericComposer(50);
        var m = composer.getMessage();

        assertNotNull(m);
        assertEquals(m.getId(), 1);
        assertTrue(m.getMessage().matches("[A-Za-z0-9]+"));
        assertEquals(m.getMessage().length(), 50);
    }
}