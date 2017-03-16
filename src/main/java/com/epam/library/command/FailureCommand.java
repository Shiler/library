package com.epam.library.command;

public class FailureCommand implements ICommand {
    private String failureMessage;

    public FailureCommand(String message) {
        this.failureMessage = message;
    }

    @Override
    public String execute(String params) {
        return this.failureMessage;
    }
}