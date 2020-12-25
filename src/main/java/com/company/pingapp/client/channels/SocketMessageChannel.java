package com.company.pingapp.client.channels;

import com.company.pingapp.configuration.PingConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

/**
 * MessageChannel implementation utilizing Socket.
 *
 * @author vv
 */
public class SocketMessageChannel implements MessageChannel {
    private Socket socket;

    private String message;
    private BufferedReader in;
    private PrintWriter out;

    public SocketMessageChannel(PingConfiguration config) {
        this.in = null;
        this.out = null;

        try {
            this.socket = new Socket(config.getHostname(), config.getPort());
        } catch (IOException e) {
            System.err.printf("%s channel could not be created: %s %n", this.getClass().getName(), e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

    }

    public boolean send(String message) {
        this.message = message;

        var sentMessage = false;

        try {
            this.out = new PrintWriter(socket.getOutputStream());
            out.println(message);
            out.flush();
            sentMessage = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // this.emulateDelay();
        return sentMessage;
    }

    public String receive() {
        String response = null;

        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = this.in.readLine();
        } catch (IOException e) {
            System.err.printf("%s could not read response: %s %n", this.getClass().getName(), e.getMessage());
            e.printStackTrace();
        }

        // this.emulateDelay();
        return response;
    }

    public void close() {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (this.out != null)
            this.out.close();

        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.gc();    // TODO: We should not be calling gc explicitly.
    }

    private void emulateDelay() {
        // Emulate delay. Sleep in range [0, 100) ms.
        try {
            long sleep = ThreadLocalRandom.current().nextLong(0L, 100L);
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
