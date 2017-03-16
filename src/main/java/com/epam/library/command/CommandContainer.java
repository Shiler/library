package com.epam.library.command;

public class CommandContainer {
    private String command;
    private String parameters;

    public void setCommand(String command) {
        this.command = command;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getCommand() {
        return command;
    }

    public String getParameters() {
        return parameters;
    }
}