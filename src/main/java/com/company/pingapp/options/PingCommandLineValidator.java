package com.company.pingapp.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Command line validator class.
 *
 * @author      vv
 */
public class PingCommandLineValidator {

    private static final int MinMessageSize = 50;
    private static final int MaxMessageSize = 3000;

    private static final int MinPortNumber = (1 << 10);
    private static final int MaxPortNumber = (1 << 16);

    private static final int MinMessagesPerSecond = 1;
    private static final int MaxMessagesPerSecond = 1000; // 1000 msg/s

    /**
     * Validates supplied command line object based on predefined business rules.
     *
     * @param commandLine
     * @throws ParseException
     */
    public void validate(CommandLine commandLine) throws ParseException {
        // Run mode is required, but mutually exclusive. Only one must be specified.
        if (!(commandLine.hasOption("p") || commandLine.hasOption("c")))
            throw new ParseException("Running mode needs to be specified. For Pitcher -p/--pitcher, and for Catcher -c/--catcher.");

        if (commandLine.hasOption("p") && commandLine.hasOption("c"))
            throw new ParseException("Running modes -p/--pitcher and -c/--catcher are mutually exclusive. Choose only one.");

        // Check for valid port number. Has to be in range (MinPortNumber, MaxPortNumber].
        var port = NumberUtils.toInt(commandLine.getOptionValue("port"), 0);
        if (port <= MinPortNumber || port > MaxPortNumber)
            throw new ParseException(String.format("Invalid port number. Needs to be in range (%s, %s].", MinPortNumber, MaxPortNumber));

        // Depending on running mode different options are required. We check only those that do not have defaults.
        if (commandLine.hasOption("p")) {
            // Pitcher mode
            if (commandLine.getOptionValue("hostname") == null)
                throw new ParseException("When running in Pitcher mode -hostname is required.");

            // Check for message per second rate. Valid range [MinMessagesPerSecond, MaxMessagesPerSecond].
            var mps = NumberUtils.toInt(commandLine.getOptionValue("mps"), 1);
            if (mps < MinMessagesPerSecond || mps > MaxMessagesPerSecond)
                throw new ParseException(String.format("Invalid mps rate. Needs to be in range [%s, %s].", MinMessagesPerSecond, MaxMessagesPerSecond));

            // Check for message size. Valid range [MinMessageSize, MaxMessageSize].
            var size = NumberUtils.toInt(commandLine.getOptionValue("size"), 300);
            if (size < MinMessageSize || size > MaxMessageSize)
                throw new ParseException(String.format("Invalid message size. Needs to be in range [%s, %s].", MinMessageSize, MaxMessageSize));

        } else {
            // Catcher mode
            if (commandLine.getOptionValue("bind") == null)
                throw new ParseException("When running in Catcher mode -bind is required.");
        }

    }

}
