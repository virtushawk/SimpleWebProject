package edu.epam.swp.model.entity;

import java.sql.Date;

/**
 * The Correction class is used to encapsulate all data needed to work with corrections.
 * @author romab
 */
public class Correction {

    private long correctionId;
    private long accountId;
    private long creatureId;
    private String text;
    private String name;
    private Date date;

    /**
     * The Correction builder is used to create Correction object.
     */
    public static class CorrectionBuilder {

        private long correctionId;
        private long accountId;
        private long creatureId;
        private String text;
        private String name;
        private Date date;

        /**
         * Instantiates a new Correction builder.
         */
        public CorrectionBuilder() {}

        /**
         * Adds correction id to the builder.
         * @param correctionId Correction's id.
         * @return CorrectionBuilder object
         */
        public CorrectionBuilder withCorrectionId(long correctionId) {
            this.correctionId = correctionId;
            return this;
        }

        /**
         * Adds User's id to the builder.
         * @param accountId User's id.
         * @return CorrectionBuilder object.
         */
        public CorrectionBuilder withAccountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        /**
         * Adds Creature's id to the builder.
         * @param creatureId Creature's id.
         * @return CorrectionBuilder object.
         */
        public CorrectionBuilder withCreatureId(long creatureId) {
            this.creatureId = creatureId;
            return this;
        }

        /**
         * Adds text to the builder.
         * @param text String containing the text.
         * @return CorrectionBuilder object.
         */
        public CorrectionBuilder withText(String text) {
            this.text = text;
            return this;
        }

        /**
         * Adds name to the builder.
         * @param name String containing the name.
         * @return CorrectionBuilder object.
         */
        public CorrectionBuilder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Adds date to the builder.
         * @param date Date object of correction.
         * @return CorrectionBuilder object.
         */
        public CorrectionBuilder withDate(Date date) {
            this.date = date;
            return this;
        }

        /**
         * Builds correction.
         * @return Correction object.
         */
        public Correction build() {
            return new Correction(this);
        }
    }

    /**
     * Creates correction with help of correction builder.
     * @param correctionBuilder CorrectionBuilder object.
     */
    private Correction(CorrectionBuilder correctionBuilder) {
        correctionId = correctionBuilder.correctionId;
        accountId = correctionBuilder.accountId;
        creatureId = correctionBuilder.creatureId;
        text = correctionBuilder.text;
        name = correctionBuilder.name;
        date = correctionBuilder.date;
    }

    public long getCorrectionId() {
        return correctionId;
    }

    public void setCorrectionId(long correctionId) {
        this.correctionId = correctionId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getCreatureId() {
        return creatureId;
    }

    public void setCreatureId(long creatureId) {
        this.creatureId = creatureId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Correction that = (Correction) o;
        return correctionId == that.correctionId && accountId == that.accountId && creatureId == that.creatureId
                && (date == null ? that.date == null : date.equals(that.date))
                && (text == null ? that.text == null : text.equals(that.text))
                && (name == null ? that.name == null : name.equals(that.name));
    }

    @Override
    public int hashCode() {
        int hash = Long.hashCode(correctionId);
        hash = hash * 31 + Long.hashCode(creatureId);
        hash = hash * 31 + Long.hashCode(accountId);
        hash = hash * 31 + (text == null ? 0 : text.hashCode());
        hash = hash * 31 + (text == null ? 0 : text.hashCode());
        hash = hash * 31 + (text == null ? 0 : text.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Correction{");
        sb.append("correctionId=").append(correctionId);
        sb.append(", accountId=").append(accountId);
        sb.append(", creatureId=").append(creatureId);
        sb.append(", text='").append(text).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}
