package edu.epam.swp.model.validation;

public class CreatureValidator {

    private static final String NAME_REGEX =  "(^([ a-z,A-Z]){1,30}$)";
    private static final String DESCRIPTION_REGEX = "(^[ ,.\r\n!\\-?{};0-9()a-zA-Z]{1,1000}$)";

    private CreatureValidator() {}

    public static boolean isName(String name) {
        return name.matches(NAME_REGEX);
    }

    public static boolean isDescription(String description) {
        return description.matches(DESCRIPTION_REGEX);
    }
}
