package by.epamtc.lyskovkirill.tasklibrary.service.validation;

import by.epamtc.lyskovkirill.tasklibrary.bean.BookGenre;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookValidation {
    private final static String TITLE_PATTERN = "\"(\\w){1,20}(_?(\\w){1,20})*\"";
    private final static String AUTHOR_PATTERN = "\"(\\w){1,20}(_?(\\w){1,20})*\"";

    public boolean isTitleValid(String title) {
        return isArgValid(TITLE_PATTERN, title);
    }

    public boolean isAuthorValid(String author) {
        return isArgValid(AUTHOR_PATTERN, author);
    }

    public boolean isGenreValid(String genre) {
        boolean isValid;
        try {
            BookGenre.valueOf(genre);
            isValid = true;
        } catch (IllegalArgumentException e) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isArgValid(String pattern, String arg) {
        Pattern patternCompile = Pattern.compile(pattern);
        Matcher matcher = patternCompile.matcher(arg);
        return matcher.matches();
    }
}
