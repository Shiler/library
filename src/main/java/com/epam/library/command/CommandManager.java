package com.epam.library.command;

import java.lang.reflect.Array;
import java.util.*;

/**
 *
 */
public class CommandManager {
    private static Map<String, ICommand> commands = new HashMap<>();
    private static Map<String, List<String>> parameters = new HashMap<>();
    private static List<String> terminateCommands = new ArrayList<>();

    static {
        commands.put(AvailableOperations.MENU_COMMAND, new ViewMenuCommand());
        commands.put(AvailableOperations.VIEW_COMMAND, new ViewCommand());
        commands.put(AvailableOperations.EXIT_COMMAND, new ExitCommand());
        commands.put(AvailableOperations.REPORT_COMMAND, new ReportCommand());
        commands.put(AvailableOperations.RENAME_COMMAND, new RenameBookCommand());

        parameters.put(AvailableOperations.VIEW_COMMAND, Arrays.asList(AvailableOperations.BOOKS_PARAM, AvailableOperations.EMPLOYEES_LONG_PARAM, AvailableOperations.EMPLOYEES_SHORT_PARAM));
        parameters.put(AvailableOperations.REPORT_COMMAND, Arrays.asList(AvailableOperations.MORE_THAN_1_PARAM, AvailableOperations.LESS_OR_EQ_THAN_2_PARAM));
        parameters.put(AvailableOperations.RENAME_COMMAND, Arrays.asList(AvailableOperations.MASK_PARAM, AvailableOperations.TITLE_PARAM));
        terminateCommands.add(AvailableOperations.EXIT_COMMAND);
    }

    public static boolean isTerminatingCommand(String command) {
        return terminateCommands.contains(command);
    }

    public ICommand defineCommand(String commandString) {
        if (commands.containsKey(commandString)) {
            return commands.get(commandString);
        } else {
            return new FailureCommand(AvailableOperations.INVALID_COMMAND_MESSAGE);
        }
    }

    public static Map<String, ICommand> getCommands() {
        return commands;
    }

    public static Map<String, List<String>> getParameters() {
        return parameters;
    }
}