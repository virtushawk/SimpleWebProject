package edu.epam.swp.tag;

import edu.epam.swp.model.entity.Correction;

import java.util.Comparator;
import java.util.List;

/**
 * CorrectionSortFunction is used to sort corrections.
 * @author romab
 */
public class CorrectionSortFunction {

    private CorrectionSortFunction() {}

    /**
     * Sorts list by correction's name.
     * @param corrections List of corrections.
     * @return List of sorted correction's.
     */
    public static List<Correction> sortByName(List<Correction> corrections) {
        Comparator<Correction> byName = (o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getName(),o2.getName());
        corrections.sort(byName);
        return corrections;
    }

    /**
     * Sorts list by correction's date.
     * @param corrections List of corrections.
     * @return List of sorted corrections.
     */
    public static List<Correction> sortByDate(List<Correction> corrections) {
        Comparator<Correction> byDate = (Comparator.comparing(Correction::getDate).reversed());
        corrections.sort(byDate);
        return corrections;
    }
}
