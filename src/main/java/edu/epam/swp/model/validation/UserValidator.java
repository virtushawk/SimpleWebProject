package edu.epam.swp.model.validation;

/**
 * UserValidator is used to validate user's data. Use in services.
 * @author romab
 */
public class UserValidator {

    private static final String USERNAME_REGEX = "(^([a-zA-Z]){1,10}$)";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String NAME_REGEX = "(^[ \\-a-zA-Z]{1,30}$)";

    private UserValidator() {}

    /**
     * Validates username.
     * @param username String containing the username.
     * @return True if username is valid, otherwise false.
     */
    public static boolean isUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }

    /**
     * Validates password.
     * @param password String containing the password.
     * @return True if password is valid, otherwise false.
     */
    public static boolean isPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    /**
     * Validates email.
     * @param email String containing the email.
     * @return True if email is valid, otherwise false.
     */
    public static boolean isEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    /**
     * Validates user's name.
     * @param name String containing the name.
     * @return True if name is valid, otherwise false.
     */
    public static boolean isName(String name) {
        return name.matches(NAME_REGEX);
    }
}
