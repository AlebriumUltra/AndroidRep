package com.example.test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProfileValidator {
    private static final String USERNAME_PATTERN =
            "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    private static final String PASSWORD_PATTERN =
            "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    private static final Pattern userNamePattern = Pattern.compile(USERNAME_PATTERN);
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean userNameIsValid(final String username) {
        Matcher matcher = userNamePattern.matcher(username);
        return matcher.matches();
    }

    public static boolean passwordIsValid(final String username) {
        Matcher matcher = passwordPattern.matcher(username);
        return matcher.matches();
    }


}
