package com.company.pingapp.client.channels;

/**
 * MessageChannel interface for different channel implementations.
 *
 * @author vv
 */
public interface MessageChannel {
    boolean send(String message);
    String receive();
    void close();
}

