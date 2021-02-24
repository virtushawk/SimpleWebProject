package edu.epam.swp.model.validation;

public class CorrectionValidator {

    private static final String NAME_REGEX =  "(^([ a-z,A-Z]){1,30}$)";
    private static final String TEXT_REGEX = "(^[ ,.\r\n!\\-?{};0-9()a-zA-Z]{1,1000}$)";

    private CorrectionValidator() {}

    public static boolean isName(String name) {
        return name.matches(NAME_REGEX);
    }

    public static boolean isText(String text) {
        return text.matches(TEXT_REGEX);
    }

}
