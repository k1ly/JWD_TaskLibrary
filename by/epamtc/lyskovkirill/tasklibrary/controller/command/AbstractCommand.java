package by.epamtc.lyskovkirill.tasklibrary.controller.command;

import by.epamtc.lyskovkirill.tasklibrary.bean.User;

public abstract class AbstractCommand implements Command {
    public static String paramSeparator = " ";
    public static String wrongSignature = "Wrong command signature\n(Type 'help [command]' to see the command signature)";

    public static User userContext = User.getGuestInstance();
}
