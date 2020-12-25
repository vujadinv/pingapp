package com.company.pingapp;

import com.company.pingapp.configuration.PingConfiguration;
import com.company.pingapp.core.PingAppFactory;
import com.company.pingapp.options.PingCommandLineOptions;
import com.company.pingapp.options.PingCommandLineParser;
import com.company.pingapp.options.PingCommandLineValidator;
import org.apache.commons.cli.*;

import java.io.IOException;

/**
 * Application entry point. Reads command line arguments, parses and validates.
 * Initializes and runs app based on specified "run mode".
 *
 * @author      vv
 */
public class PingApp {

    public static void main(String[] args) {

        PingCommandLineOptions options = new PingCommandLineOptions();
        CommandLine commandLine = null;

        try {
            commandLine = new PingCommandLineParser(options, args, new DefaultParser(), new PingCommandLineValidator()).parse();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            options.printHelp(PingApp.class.getName());
        }

        if (commandLine == null)
            System.exit(1);

        var config = new PingConfiguration(commandLine);

        var app = new PingAppFactory().getApp(config);

        if (app == null) {
            System.err.println("Internal error. Required app not implemented.");
            System.exit(1);
        }

        System.out.println("Starting..");

        // Add shutdown hook for shutdown request.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> app.shutdown()));

        try {
            app.run();
            boolean awaiting = app.await();
        } catch (IOException e) {
            System.err.printf("%s could not be started: %s %n", app.getClass().getName() ,e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.printf("%s can not await: %s %n", app.getClass().getName() ,e.getMessage());
            e.printStackTrace();
        }

    }
}
