package com.epam.library.controller;

import com.epam.library.command.CommandContainer;
import com.epam.library.command.CommandManager;
import com.epam.library.command.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    private static final Logger LOG = LogManager.getLogger();
    private CommandManager commandManager;

    public Controller() {
        commandManager = new CommandManager();
    }

    public CommandContainer read() {
        CommandContainer commandContainer = new CommandContainer();
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            LOG.info("Input:");
            String input = bufferRead.readLine();
            input = input.trim();
            int delimiter = input.indexOf(" ");
            String commandString = null;
            String paramString = "";
            if (delimiter > 0) {
                commandString = input.substring(0, delimiter).trim();
                paramString = input.substring(delimiter).trim();
            } else if (!input.isEmpty()) {
                commandString = input;
            }

            commandContainer.setCommand(commandString);
            commandContainer.setParameters(paramString);
        } catch (IOException e) {
            LOG.fatal("Error while reading from console", e);
            throw new RuntimeException(e);
        }

        return commandContainer;
    }

    public void process(CommandContainer commandContainer) {
        ICommand command = commandManager.defineCommand(commandContainer.getCommand());
        print(command.execute(commandContainer.getParameters()));
    }

    private void print(String response) {
        System.out.println(response);
    }
}
