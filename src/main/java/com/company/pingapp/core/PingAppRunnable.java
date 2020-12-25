package com.company.pingapp.core;

import java.io.IOException;

/**
 * Interface that specifies formal requirements for a ping apps (server/client).
 *
 * @author      vv
 */

public interface PingAppRunnable {
    void run() throws IOException;
    boolean await() throws InterruptedException;
    void shutdown();
}
