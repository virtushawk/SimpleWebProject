package edu.epam.swp.model.entity;

import java.sql.Date;

public class Correction {

    private long correctionId;
    private long accountId;
    private long creatureId;
    private String text;
    private String name;
    private Date date;

    public Correction() {}

    public Correction(long correctionId, long accountId, long creatureId, String text, String name, Date date) {
        this.correctionId = correctionId;
        this.accountId = accountId;
        this.creatureId = creatureId;
        this.text = text;
        this.name = name;
        this.date = date;
    }

    public Correction(long accountId, long creatureId, String text, String name, Date date) {
        this.accountId = accountId;
        this.creatureId = creatureId;
        this.text = text;
        this.name = name;
        this.date = date;
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
                && date.equals(that.date) && text.equals(that.text) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int hashcode = (int) accountId;
        hashcode = hashcode * 31 + (int) creatureId;
        hashcode = hashcode * 31 + text.hashCode();
        hashcode = hashcode * 31 + name.hashCode();
        hashcode = hashcode * 31 + date.hashCode();
        return hashcode;
    }
}
