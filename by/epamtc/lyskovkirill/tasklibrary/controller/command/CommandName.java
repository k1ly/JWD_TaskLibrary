package by.epamtc.lyskovkirill.tasklibrary.controller.command;

public enum CommandName {
    HELP,

    SIGN_IN(" [login] [password]"),
    SIGN_OUT,
    REGISTER(" [login] [password] [name]"),
    UPDATE(" [updating attribute] [new attribute] [password]"),
    DELETE(" [password]"),

    ADD_BOOK(" [title] [author] [genre]"),
    EDIT_BOOK(" [title] [author] [editing attribute] [new attribute]"),
    DELETE_BOOK(" [title] [author]"),
    FIND_BOOKS_BY(" [attribute] [searching filter]"),
    SHOW_BOOKS,
    SHOW_FAVOURITES,
    ADD_TO_FAVOURITES(" [title] [author]"),
    REMOVE_FROM_FAVOURITES(" [title] [author]"),

    EXIT,
    WRONG_REQUEST;

    private final String arguments;

    CommandName(){
        arguments = "";
    }

    CommandName(String arguments){
        this.arguments = arguments;
    }

    public String getArguments() {
        return arguments;
    }
}
