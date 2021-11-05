package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.controller.command.AbstractCommand;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.CommandName;

public class Help extends AbstractCommand {

    @Override
    public String execute(String request) {
        String response;
        StringBuilder responseBuilder = new StringBuilder();

        CommandName command;
        String[] requestParams;
        if (request.isEmpty()) {
            responseBuilder.append("Commands:\n");
            for (int i = 0; i < CommandName.values().length - 1; i++) {
                responseBuilder.append(CommandName.values()[i]).append('\n');
            }
            response = responseBuilder.toString();
        } else {
            requestParams = request.split(paramSeparator);
            if (requestParams.length != 1)
                response = wrongSignature;
            else {
                try {
                    command = CommandName.valueOf(requestParams[0].toUpperCase());
                    responseBuilder.append(command).append(" command signature:").append('\n')
                            .append(command).append(command.getArguments()).append('\n');
                    response = responseBuilder.toString();
                } catch (IllegalArgumentException e) {
                    response = "Wrong command parameter";
                }
            }
        }
        return response;
    }
}