package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.controller.command.AbstractCommand;

public class Exit extends AbstractCommand {

    @Override
    public String execute(String request) {
        String response = null;
        if (request.length() > 0)
            response = wrongSignature;
        return response;
    }
}