package edu.epam.swp.model.entity;

import java.sql.Date;

public class Creature {

    private long id;
    private long accountId;
    private CreatureStatus creatureStatus;
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

    public Creature(long id,String name, String description, Date lastUpdated) {
        this.id = id;
        this.name = name;
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

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public CreatureStatus getCreatureStatus() {
        return creatureStatus;
    }

    public void setCreatureStatus(CreatureStatus creatureStatus) {
        this.creatureStatus = creatureStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creature creature = (Creature) o;
        return  lastUpdated.equals(creature.lastUpdated) && id == creature.id && accountId == creature.accountId
                && name.equals(creature.name) && picture.equals(creature.picture) && creatureStatus.equals(creature.creatureStatus)
                && description.equals(creature.description);
    }

    @Override
    public int hashCode() {
        int hashcode = (int) id;
        hashcode = hashcode * 31 + (int) accountId;
        hashcode = hashcode * 31 + name.hashCode();
        hashcode = hashcode * 31 + picture.hashCode();
        hashcode = hashcode * 31 + description.hashCode();
        hashcode = hashcode * 31 + lastUpdated.hashCode();
        hashcode = hashcode * 31 + creatureStatus.hashCode();
        return hashcode;
    }
}
