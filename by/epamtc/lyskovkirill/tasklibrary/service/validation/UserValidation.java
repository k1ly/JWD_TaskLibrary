package by.epamtc.lyskovkirill.tasklibrary.service.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation {
    private final static String COMMON_PATTERN = "([\\w_])";
    private final static String LOGIN_PATTERN = COMMON_PATTERN + "{5,20}";
    private final static String PASSWORD_PATTERN = COMMON_PATTERN + "{8,20}";
    private final static String NAME_PATTERN = COMMON_PATTERN + "{4,30}";

    public boolean isLoginValid(String login) {
        return isArgValid(LOGIN_PATTERN, login);
    }

    public boolean isPasswordValid(String password) {
        return isArgValid(PASSWORD_PATTERN, password);
    }

    public boolean isNameValidName(String name) {
        return isArgValid(NAME_PATTERN, name);
    }

    private boolean isArgValid(String pattern, String arg) {
        Pattern patternCompile = Pattern.compile(pattern);
        Matcher matcher = patternCompile.matcher(arg);
        return matcher.matches();
    }
}
