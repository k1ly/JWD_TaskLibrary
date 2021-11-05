package by.epamtc.lyskovkirill.tasklibrary.service.validation;

import by.epamtc.lyskovkirill.tasklibrary.bean.BookGenre;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookValidation {

    public static boolean validateTitle(String title) {
        return validate("\"(\\w){1,20}(_?(\\w){1,20})*\"", title);
    }

    public static boolean validateAuthor(String author) {
        return validate("((\\w\\.)|(_?\\w{2,20})){1,3}", author);
    }

    public static boolean validateGenre(String genre) {
        boolean isValid;
        try {
            BookGenre.valueOf(genre);
            isValid = true;
        } catch (IllegalArgumentException e) {
            isValid = false;
        }
        return isValid;
    }

    private static boolean validate(String pattern, String arg) {
        Pattern patternCompile = Pattern.compile(pattern);
        Matcher matcher = patternCompile.matcher(arg);
        return matcher.matches();
    }
}
