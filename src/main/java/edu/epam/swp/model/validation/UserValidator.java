package edu.epam.swp.model.validation;

public class UserValidator {
    private static final String USERNAME_REGEX = "(^([a-zA-Z]){1,10}$)";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String NAME_REGEX = "(^[ \\-a-zA-Z]{1,30}$)";

    private UserValidator() {}

    public static boolean isUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }

    public static boolean isPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isName(String name) {
        return name.matches(NAME_REGEX);
    }
}
