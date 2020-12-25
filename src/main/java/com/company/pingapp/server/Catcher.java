package com.company.pingapp.server;

import com.company.pingapp.core.PingAppRunnable;
import com.company.pingapp.configuration.PingConfiguration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Catcher/Server class implements PingAppRunnable interface and can be started from the command line.
 *
 * @author      vv
 */
public class Catcher implements PingAppRunnable {
    private static final int ThreadPoolSize = 32;  // TODO: Should be configurable
    private static final int SocketQueueSize = 16;  // TODO: Should be configurable

    private final ScheduledExecutorService workers;
    private final PingConfiguration config;
    private ServerSocket socket;
    private boolean isActive;

    public Catcher(PingConfiguration config) {
        this.workers = Executors.newScheduledThreadPool(ThreadPoolSize);
        this.config = config;
        this.isActive = true;
    }

    /**
     * Sets up socket based on configuration options and listens for incoming connections. After
     * accepting connection designated worker will be executed to handle client on a separate thread.
     *
     * @throws IOException
     */
    @Override
    public void run()  throws IOException {
        System.out.printf("%s STARTED at: %s%n", this.getClass().getName(), LocalDateTime.now());

        var bindAddress = InetAddress.getByName(this.config.getBindAddress());

        this.socket = new ServerSocket(this.config.getPort(), SocketQueueSize, bindAddress);
        this.socket.setSoTimeout(1000); // TODO: Timeout value should be configurable

        System.out.printf("%s listening.. %s:%s%n", this.getClass().getName(), this.config.getBindAddress(), this.config.getPort());

        while (isActive) {
            try {
                handleConnection();
            } catch (SocketTimeoutException e) {
                // Ignore
            }
        }
    }

    private void handleConnection() throws IOException {
        var clientSocket = this.socket.accept();

        System.out.printf("%s Accepted connection from: %s%n",
                this.getClass().getName(),
                clientSocket.getRemoteSocketAddress()
        );

        MessageHandler handler = new MessageHandler(clientSocket);
        this.workers.execute(handler);
    }

    @Override
    public void shutdown() {
        System.out.printf("%s SHUTDOWN in progress%n", this.getClass().getName());
        this.isActive = false;
        this.workers.shutdown();
        System.out.printf("%s FINISHED%n", this.getClass().getName());
    }

    @Override
    public boolean await() throws InterruptedException {
        return this.workers.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

}
