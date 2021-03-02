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
    private double averageRating;

    public static class CreatureBuilder {
        private long creatureId;
        private long accountId;
        private CreatureStatus creatureStatus;
        private String name;
        private String picture;
        private String description;
        private Date lastUpdated;
        private double averageRating;

        public CreatureBuilder() {}

        public CreatureBuilder withId(long id) {
            this.creatureId = id;
            return this;
        }

        public CreatureBuilder withAccountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        public CreatureBuilder withCreatureStatus(CreatureStatus creatureStatus) {
            this.creatureStatus = creatureStatus;
            return this;
        }

        public CreatureBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CreatureBuilder withPicture(String picture) {
            this.picture = picture;
            return this;
        }

        public CreatureBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public CreatureBuilder withLastUpdated(Date lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public CreatureBuilder withAverageRating(double averageRating) {
            this.averageRating = averageRating;
            return this;
        }

        public Creature build() {
            return new Creature(this);
        }
    }

    private Creature(CreatureBuilder creatureBuilder) {
        id = creatureBuilder.creatureId;
        accountId = creatureBuilder.accountId;
        creatureStatus = creatureBuilder.creatureStatus;
        name = creatureBuilder.name;
        picture = creatureBuilder.picture;
        description = creatureBuilder.description;
        lastUpdated = creatureBuilder.lastUpdated;
        averageRating = creatureBuilder.averageRating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creature creature = (Creature) o;
        return  id == creature.id && accountId == creature.accountId
                && averageRating == creature.averageRating && (name == null ? creature.name == null : name.equals(creature.name))
                && (picture == null ? creature.picture == null : picture.equals(creature.picture))
                && (creatureStatus == null ? creature.creatureStatus == null : creatureStatus.equals(creature.creatureStatus))
                && (description == null ? creature.description == null : description.equals(creature.description))
                && (lastUpdated == null ? creature.lastUpdated == null : lastUpdated.equals(creature.lastUpdated));
    }

    @Override
    public int hashCode() {
        int hashcode = Long.hashCode(id);
        hashcode = hashcode * 31 + Long.hashCode(accountId);
        hashcode = hashcode * 31 + (name == null ? 0 : name.hashCode());
        hashcode = hashcode * 31 + (picture == null ? 0 : picture.hashCode());
        hashcode = hashcode * 31 + (description == null ? 0 : description.hashCode());
        hashcode = hashcode * 31 +(lastUpdated == null ? 0 : lastUpdated.hashCode());
        hashcode = hashcode * 31 + (creatureStatus == null ? 0 : creatureStatus.hashCode());
        hashcode = hashcode * 31 + Double.hashCode(averageRating);
        return hashcode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Creature{");
        sb.append("id=").append(id);
        sb.append(", accountId=").append(accountId);
        sb.append(", creatureStatus=").append(creatureStatus);
        sb.append(", name='").append(name).append('\'');
        sb.append(", picture='").append(picture).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", lastUpdated=").append(lastUpdated);
        sb.append(", averageRating=").append(averageRating);
        sb.append('}');
        return sb.toString();
    }
}
