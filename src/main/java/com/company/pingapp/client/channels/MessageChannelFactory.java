package com.company.pingapp.client.channels;


import com.company.pingapp.configuration.PingConfiguration;

public class MessageChannelFactory {

    public static MessageChannel create(MessageChannelType type, PingConfiguration config) {

        if (type == MessageChannelType.SOCKET)
            return new SocketMessageChannel(config);
        else
            return new DummyMessageChannel();

    }

}