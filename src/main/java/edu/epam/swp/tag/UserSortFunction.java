package edu.epam.swp.tag;

import edu.epam.swp.model.entity.User;

import java.util.Comparator;
import java.util.List;

/**
 * UserSortFunction is used to sort users.
 */
public class UserSortFunction {

    private UserSortFunction() {}

    /**
     * Sorts list by username.
     * @param users List of users.
     * @return List of sorted users.
     */
    public static List<User> sortByUsername(List<User> users) {
        Comparator<User> byName = (o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getUsername(),o2.getUsername());
        users.sort(byName);
        return users;
    }

    /**
     * Sorts list by user's role.
     * @param users List of users.
     * @return List of sorted users.
     */
    public static List<User> sortByRole(List<User> users) {
        Comparator<User> byRole = (Comparator.comparing(User::getRole));
        users.sort(byRole);
        return users;
    }

}
