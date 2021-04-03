package edu.epam.swp.model.validation;

/**
 * CreatureValidator class is used to validate creature's data. Used in services.
 * @author romab
 */
public class CreatureValidator {

    private static final String NAME_REGEX =  "(^([ a-z,A-Z]){1,30}$)";
    private static final String DESCRIPTION_REGEX = "(^[\n\r ,.!'—\\-?{}\":;()\\w]{1,1000}$)";

    private CreatureValidator() {}

    /**
     * Validates creature's name.
     * @param name String containing the name.
     * @return True if name is valid, otherwise false.
     */
    public static boolean isName(String name) {
        return name.matches(NAME_REGEX);
    }

    /**
     * Validates creature's description.
     * @param description String containing the description.
     * @return True if description is valid, otherwise false.
     */
    public static boolean isDescription(String description) {
        return description.matches(DESCRIPTION_REGEX);
    }
}
