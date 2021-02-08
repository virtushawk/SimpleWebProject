package edu.epam.swp.model.entity;

import java.sql.Date;

public class Review {

    String avatar;
    String accountName;
    long reviewId;
    long accountId;
    long creatureId;
    String text;
    int rating;
    Date time;

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Review(long accountId, long creatureId, String text, Date time, int rating) {
        this.accountId = accountId;
        this.creatureId = creatureId;
        this.text = text;
        this.time = time;
        this.rating= rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return reviewId == review.reviewId && accountId == review.accountId && creatureId == review.creatureId &&
                text.equals(review.text) && time.equals(review.time) && rating == review.rating;
    }

    @Override
    public int hashCode() {
        int hash = (int) reviewId;
        hash = hash * 31 + (int) accountId;
        hash = hash * 31 + (int) creatureId;
        hash = hash * 31 + text.hashCode();
        hash = hash * 31 + time.hashCode();
        hash = hash * 31 + rating;
        return hash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Review{");
        sb.append("reviewId=").append(reviewId);
        sb.append(", accountId=").append(accountId);
        sb.append(", creatureId=").append(creatureId);
        sb.append(", text='").append(text).append('\'');
        sb.append(", rating=").append(rating);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
