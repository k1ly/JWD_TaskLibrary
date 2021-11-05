package by.epamtc.lyskovkirill.tasklibrary.controller;

import by.epamtc.lyskovkirill.tasklibrary.controller.command.Command;

public final class Controller {
    private final static String paramSeparator = " ";

    private final CommandProvider provider = new CommandProvider();

    public String executeTask(String request) {
        String commandName;
        Command executionCommand;

        commandName = request.contains(paramSeparator) ? request.substring(0, request.indexOf(paramSeparator)) : request;
        executionCommand = provider.getCommand(commandName);

        String response;
        request = request.contains(paramSeparator) ? request.substring(request.indexOf(paramSeparator) + paramSeparator.length()) : "";
        response = executionCommand.execute(request);

        return response;
    }
}
