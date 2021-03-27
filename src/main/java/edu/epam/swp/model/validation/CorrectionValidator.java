package edu.epam.swp.model.validation;

/**
 * CorrectionValidator class used to validate correction's data. Used in services.
 * @author romab
 */
public class CorrectionValidator {

    private static final String NAME_REGEX =  "(^([ a-z,A-Z]){1,30}$)";
    private static final String TEXT_REGEX = "(^[\n\r ,.!'—\\-?{}\";()\\w]{1,1000}$)";

    private CorrectionValidator() {}

    /**
     * Validates correction's name.
     * @param name String containing the name.
     * @return True if name is valid, otherwise false.
     */
    public static boolean isName(String name) {
        return name.matches(NAME_REGEX);
    }

    /**
     * Validates correction's text.
     * @param text String containing the text.
     * @return True if text is valid, otherwise false.
     */
    public static boolean isText(String text) {
        return text.matches(TEXT_REGEX);
    }

}
