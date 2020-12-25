package com.company.pingapp.client.messages;

import com.company.pingapp.core.Counter;

/**
 * Concrete implementation of MessageComposer interface.
 *
 * Class is responsible for creating identifiable alphanumeric messages of specified size.
 *
 * @author      vv
 */
public class AlphaNumericComposer implements MessageComposer {
    private static String AlphaNumericAscii = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int size;

    public AlphaNumericComposer(int size) {
        this.size = size;
    }


    /**
     * Creates identifiable but randomly generated alphanumeric messages.
     *
     * @return Message
     */
    public Message getMessage() {
        StringBuilder sb = new StringBuilder(this.size);

        for (int i = 0; i < this.size; i++) {
            int index = (int)(AlphaNumericAscii.length() * Math.random());
            sb.append(AlphaNumericAscii.charAt(index));
        }

        return new Message(Counter.nextId(), sb.toString());
    }
}
