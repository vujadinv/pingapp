package com.company.pingapp.core;

import com.company.pingapp.client.Pitcher;
import com.company.pingapp.configuration.PingConfiguration;
import com.company.pingapp.server.Catcher;

/**
 * Provides factory method for creating ping apps (server/client) based on
 * provided configuration option for run mode. Provided config is passed on as is.
 *
 * @author      vv
 */
public class PingAppFactory {

    /**
     * @param config Runtime configuration options
     * @return Configured server/client app if available, null if not.
     */
    public PingAppRunnable getApp(PingConfiguration config){

        if (config.getRunMode() == PingRunMode.PITCHER)
            return new Pitcher(config);
        else if (config.getRunMode() == PingRunMode.CATCHER)
            return new Catcher(config);
        else
            return null;

    }
}
