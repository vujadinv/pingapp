package com.company.pingapp.server;

import java.io.*;
import java.net.Socket;

/**
 * Handles incoming messages on provided client socket.
 *
 * @author vv
 */
public class MessageHandler implements Runnable {

    private final Socket clientSocket;


    public MessageHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Reads the message from the client, and echoes the same message back.
     *
     * TODO: Message size is not specified and buffer sizes not enforced.
     */
    @Override
    public void run() {

        try {
            // Get the input stream of client
            BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

            // Get output stream for the client
            PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream());

            var inputString = in.readLine();

            // Write back the same line
            out.write(inputString);
            out.flush();

            // Cleanup. We ignore any further client input.
            this.cleanup(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void cleanup(BufferedReader in, PrintWriter out) throws IOException {
        try {
            if (in != null)
                in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (out != null)
            out.close();

        this.clientSocket.close();

        System.gc();    // TODO: We should not be calling gc explicitly.
    }
}