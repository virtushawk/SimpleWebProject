package edu.epam.swp.tag;

import edu.epam.swp.model.entity.Creature;

import java.util.Comparator;
import java.util.List;

public class CreatureSortFunction {

    private CreatureSortFunction() {}

    public static List<Creature> sortByName(List<Creature> creatures) {
        Comparator<Creature> byName = (o1,o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getName(),o2.getName());
        creatures.sort(byName);
        return creatures;
    }

    public static List<Creature> sortByDate(List<Creature> creatures) {
        Comparator<Creature> byDate = (Comparator.comparing(Creature::getLastUpdated).reversed());
        creatures.sort(byDate);
        return creatures;
    }

    public static List<Creature> sortByRating(List<Creature> creatures) {
        Comparator<Creature> byRating = (Comparator.comparing(Creature::getAverageRating).reversed());
        creatures.sort(byRating);
        return creatures;
    }
}
