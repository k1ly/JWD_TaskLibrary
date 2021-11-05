package by.epamtc.lyskovkirill.tasklibrary.service.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation {
    private final static String COMMON_PATTERN = "([\\w_\\d])";

    public static boolean validateLogin(String login) {
        return validate(COMMON_PATTERN + "{5,20}", login);
    }

    public static boolean validatePassword(String password) {
        return validate(COMMON_PATTERN + "{8,20}", password);
    }

    public static boolean validateName(String name) {
        return validate(COMMON_PATTERN + "{4,30}", name);
    }

    private static boolean validate(String pattern, String arg) {
        Pattern patternCompile = Pattern.compile(pattern);
        Matcher matcher = patternCompile.matcher(arg);
        return matcher.matches();
    }
}
