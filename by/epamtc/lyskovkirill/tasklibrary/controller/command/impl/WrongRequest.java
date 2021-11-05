package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.controller.command.Command;

public class WrongRequest implements Command {

    @Override
    public String execute(String request) {
        return "Wrong request command!\n(Type 'help' for command list)";
    }
}