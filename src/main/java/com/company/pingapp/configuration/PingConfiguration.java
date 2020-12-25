package com.company.pingapp.configuration;

import com.company.pingapp.core.PingRunMode;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.math.NumberUtils;


/**
 * Mapper class for CommandLine. Extracts runtime options by enforcing correct
 * types and setting default values.
 *
 * @author      vv
 */
public class PingConfiguration  {

    private final PingRunMode runMode;
    private final int size;
    private final int mps;
    private final String host;
    private final String bind;
    private final int port;

    public PingConfiguration() {
        // TODO: Default values should be retrieved from config
        this.runMode = PingRunMode.UNKNOWN;
        this.size = 300;
        this.mps = 1;
        this.host = "localhost";
        this.bind = "127.0.0.1";
        this.port = 10001;
    }

    public PingConfiguration(CommandLine commandLine) {
        this(commandLine.hasOption("p"),
                commandLine.hasOption("c"),
                commandLine.getOptionValue("size"),
                commandLine.getOptionValue("mps"),
                commandLine.getOptionValue("hostname"),
                commandLine.getOptionValue("bind"),
                commandLine.getOptionValue("port")
        );
    }

    public PingConfiguration(boolean isPitcher, boolean isCatcher, String size, String mps, String host, String bind, String port) {
        if (isPitcher)
            this.runMode = PingRunMode.PITCHER;
        else if (isCatcher)
            this.runMode = PingRunMode.CATCHER;
        else
            this.runMode = PingRunMode.UNKNOWN;

        this.size = NumberUtils.toInt(size, 300);

        this.mps = NumberUtils.toInt(mps, 1);

        this.host = host;

        this.bind = bind;

        this.port = NumberUtils.toInt(port, 0);
    }

    public PingRunMode getRunMode() {
        return this.runMode;
    }

    public int getMps() {
        return this.mps;
    }

    public int getSize() {
        return this.size;
    }

    public int getPort() {
        return this.port;
    }

    public String getHostname() {
        return this.host;
    }

    public String getBindAddress() {
        return this.bind;
    }
}
