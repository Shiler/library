package com.epam.library.command;

public class ExitCommand implements ICommand {

    @Override
    public String execute(String command) {
        return AvailableOperations.EXIT_MESSAGE;
    }
}