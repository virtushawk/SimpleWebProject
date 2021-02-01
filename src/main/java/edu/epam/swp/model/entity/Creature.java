package edu.epam.swp.model.entity;

import java.sql.Date;

public class Creature {

    private long id;
    private String name;
    private String picture;
    private String description;
    private Date lastUpdated;

    public Creature(String name, String picture, String description, Date lastUpdated) {
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.lastUpdated = lastUpdated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creature creature = (Creature) o;
        return  lastUpdated.equals(creature.lastUpdated) && id == creature.id
                && name.equals(creature.name) && picture.equals(creature.picture)
                && description.equals(creature.description);
    }

    @Override
    public int hashCode() {
        int hashcode = (int) id;
        hashcode = hashcode * 31 + name.hashCode();
        hashcode = hashcode * 31 + picture.hashCode();
        hashcode = hashcode * 31 + description.hashCode();
        hashcode = hashcode * 31 + lastUpdated.hashCode();
        return hashcode;
    }
}
