package edu.epam.swp.model.entity;

import java.util.UUID;

public class Creature {

    private String id = UUID.randomUUID().toString();
    private String name;
    private String picture;
    private String description;
    private int rating;
    private long lastUpdated;

    public Creature(String name, String picture, String description, int rating, long lastUpdated) {
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.rating = rating;
        this.lastUpdated = lastUpdated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creature creature = (Creature) o;
        return rating == creature.rating && lastUpdated == creature.lastUpdated && id.equals(creature.id)
                && name.equals(creature.name) && picture.equals(creature.picture)
                && description.equals(creature.description);
    }

    @Override
    public int hashCode() {
        int hashcode = name.hashCode();
        hashcode = hashcode * 31 + picture.hashCode();
        hashcode = hashcode * 31 + description.hashCode();
        hashcode = hashcode * 31 + rating;
        hashcode = hashcode * 31 + (int) lastUpdated;
        return hashcode;
    }
}
