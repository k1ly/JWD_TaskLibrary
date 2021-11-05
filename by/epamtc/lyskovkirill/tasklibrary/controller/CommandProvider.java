package by.epamtc.lyskovkirill.tasklibrary.controller;

import by.epamtc.lyskovkirill.tasklibrary.controller.command.Command;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.CommandName;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    Map<CommandName, Command> repository = new HashMap<>();

    public CommandProvider() {
        repository.put(CommandName.HELP, new Help());

        repository.put(CommandName.SIGN_IN, new SignIn());
        repository.put(CommandName.SIGN_OUT, new SignOut());
        repository.put(CommandName.REGISTER, new RegisterUser());
        repository.put(CommandName.UPDATE, new UpdateUser());
        repository.put(CommandName.DELETE, new DeleteUser());

        repository.put(CommandName.ADD_BOOK, new AddBook());
        repository.put(CommandName.EDIT_BOOK, new EditBook());
        repository.put(CommandName.DELETE_BOOK, new DeleteBook());
        repository.put(CommandName.FIND_BOOKS_BY, new FindBooksBy());
        repository.put(CommandName.SHOW_BOOKS, new ShowBooks());
        repository.put(CommandName.SHOW_FAVOURITES, new ShowFavourites());
        repository.put(CommandName.ADD_TO_FAVOURITES, new AddToFavourites());
        repository.put(CommandName.REMOVE_FROM_FAVOURITES, new RemoveFromFavourites());

        repository.put(CommandName.EXIT, new Exit());
        repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
    }

    public Command getCommand(String name) {
        CommandName commandName;
        Command command;

        try {
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            command = repository.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }
}
