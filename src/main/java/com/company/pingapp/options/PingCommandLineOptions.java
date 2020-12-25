package com.company.pingapp.options;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Creates command line options.
 *
 * @author      vv
 */
public class PingCommandLineOptions {
    final private Options options;
    final private HelpFormatter formatter;

    public PingCommandLineOptions() {
        this.options = new Options();
        this.formatter = new HelpFormatter();
        this.create();
    }

    private void create() {

        var pitcher = new Option("p", "pitcher", false, "Specifies operation mode for Pitcher.");
        this.options.addOption(pitcher);

        var catcher = new Option("c", "catcher", false, "Specifies operation mode for Catcher.");
        this.options.addOption(catcher);

        var mps = new Option("mps", "messages-per-second", true, "Messages per second rate (Pitcher). Default is 1.");
        this.options.addOption(mps);

        var size = new Option("size", "size", true, "Message size (Pitcher). Default is 3000 bytes.");
        this.options.addOption(size);

        var bind = new Option("bind", "bind", true, "Binding address (Catcher). Required.");
        this.options.addOption(bind);

        var port = new Option("port", "port", true, "Port number (Pitcher/Catcher). Required.");
        port.setRequired(true);
        this.options.addOption(port);

        var host = new Option("hostname", "hostname", true, "Hostname (Pitcher). Required");
        this.options.addOption(host);

    }

    public Options getOptions() {
        return this.options;
    }

    public void printHelp(String cmdLineSyntax) {
        this.formatter.printHelp(cmdLineSyntax, this.options);
    }
}
