package edu.epam.swp.model.entity;

import java.sql.Date;

/**
 * The Creature class is used to encapsulate all data needed to work with creature.
 * @author romab
 */
public class Creature {

    private long creatureId;
    private long accountId;
    private CreatureStatus creatureStatus;
    private String name;
    private String picture;
    private String description;
    private Date lastUpdated;
    private double averageRating;

    /**
     * The Creature builder is used to create creature object.
     */
    public static class CreatureBuilder {
        private long creatureId;
        private long accountId;
        private CreatureStatus creatureStatus;
        private String name;
        private String picture;
        private String description;
        private Date lastUpdated;
        private double averageRating;

        /**
         * Instantiates a new Creature builder.
         */
        public CreatureBuilder() {}

        /**
         * Adds creature's id to the builder.
         * @param creatureId Creature's id.
         * @return CreatureBuilder object.
         */
        public CreatureBuilder withCreatureId(long creatureId) {
            this.creatureId = creatureId;
            return this;
        }

        /**
         * Adds User's id to the builder.
         * @param accountId User's id.
         * @return CreatureBuilder object.
         */
        public CreatureBuilder withAccountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        /**
         * Adds creature status to the builder.
         * @param creatureStatus Status of the creature.
         * @return CreatureBuilder object.
         */
        public CreatureBuilder withCreatureStatus(CreatureStatus creatureStatus) {
            this.creatureStatus = creatureStatus;
            return this;
        }

        /**
         * Adds the name to the builder.
         * @param name String containing the name.
         * @return CreatureBuilder object.
         */
        public CreatureBuilder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Adds the picture to the builder.
         * @param picture String containing the path to the picture.
         * @return CreatureBuilder object.
         */
        public CreatureBuilder withPicture(String picture) {
            this.picture = picture;
            return this;
        }

        /**
         * Adds the description to the builder.
         * @param description String containing the description.
         * @return CreatureBuilder object.
         */
        public CreatureBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Adds date to the builder.
         * @param lastUpdated Date object.
         * @return CreatureBuilder object.
         */
        public CreatureBuilder withLastUpdated(Date lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        /**
         * Adds average rating to the builder.
         * @param averageRating average rating.
         * @return CreatureBuilder object.
         */
        public CreatureBuilder withAverageRating(double averageRating) {
            this.averageRating = averageRating;
            return this;
        }

        /**
         * Builds creature.
         * @return Creature object.
         */
        public Creature build() {
            return new Creature(this);
        }
    }

    /**
     * Creates Creature object with help of creature builder.
     * @param creatureBuilder object.
     */
    private Creature(CreatureBuilder creatureBuilder) {
        creatureId = creatureBuilder.creatureId;
        accountId = creatureBuilder.accountId;
        creatureStatus = creatureBuilder.creatureStatus;
        name = creatureBuilder.name;
        picture = creatureBuilder.picture;
        description = creatureBuilder.description;
        lastUpdated = creatureBuilder.lastUpdated;
        averageRating = creatureBuilder.averageRating;
    }

    public long getCreatureId() {
        return creatureId;
    }

    public void setCreatureId(long creatureId) {
        this.creatureId = creatureId;
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
        return  creatureId == creature.creatureId && accountId == creature.accountId
                && averageRating == creature.averageRating && (name == null ? creature.name == null : name.equals(creature.name))
                && (picture == null ? creature.picture == null : picture.equals(creature.picture))
                && (creatureStatus == null ? creature.creatureStatus == null : creatureStatus.equals(creature.creatureStatus))
                && (description == null ? creature.description == null : description.equals(creature.description))
                && (lastUpdated == null ? creature.lastUpdated == null : lastUpdated.equals(creature.lastUpdated));
    }

    @Override
    public int hashCode() {
        int hashcode = Long.hashCode(creatureId);
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
        sb.append("id=").append(creatureId);
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
