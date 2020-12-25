package com.company.pingapp.options;

import org.apache.commons.cli.*;

/**
 * Wrapper class for parsing and validating command line arguments.
 *
 * @author      vv
 */

public class PingCommandLineParser {

    private final PingCommandLineOptions options;
    private final String[] args;
    private final CommandLineParser parser;
    private final PingCommandLineValidator validator;
    
    public PingCommandLineParser(PingCommandLineOptions options, String[] args, CommandLineParser parser, PingCommandLineValidator validator) {
        this.options = options;
        this.args = args;
        this.parser =  parser;
        this.validator = validator;
    }

    /**
     * Parses and validates command line arguments using provided parser, options and validator.
     * @return CommandLine
     * @throws ParseException
     */
    public CommandLine parse() throws ParseException {
        var commandLine = this.parser.parse(this.options.getOptions(), this.args);

        validator.validate(commandLine);

        return commandLine;
    }

}
