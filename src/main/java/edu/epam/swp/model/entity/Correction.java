package edu.epam.swp.model.entity;

import java.sql.Date;

public class Correction {

    private long correctionId;
    private long accountId;
    private long creatureId;
    private String text;
    private String name;
    private Date date;

    public static class CorrectionBuilder {

        private long correctionId;
        private long accountId;
        private long creatureId;
        private String text;
        private String name;
        private Date date;

        public CorrectionBuilder() {}

        public CorrectionBuilder withCorrectionId(long correctionId) {
            this.correctionId = correctionId;
            return this;
        }

        public CorrectionBuilder withAccountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        public CorrectionBuilder withCreatureId(long creatureId) {
            this.creatureId = creatureId;
            return this;
        }

        public CorrectionBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public CorrectionBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CorrectionBuilder withDate(Date date) {
            this.date = date;
            return this;
        }

        public Correction build() {
            return new Correction(this);
        }
    }

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
