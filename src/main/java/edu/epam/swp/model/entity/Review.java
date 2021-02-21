package edu.epam.swp.model.entity;

import java.sql.Date;

public class Review {

    private long reviewId;
    private long accountId;
    private long creatureId;
    private int rating;
    private String avatar;
    private String accountName;
    private String creatureName;
    private String text;
    private Date date;

    public static class ReviewBuilder {

        private long reviewId;
        private long accountId;
        private long creatureId;
        private int rating;
        private String avatar;
        private String accountName;
        private String creatureName;
        private String text;
        private Date date;

        public ReviewBuilder() {}

        public ReviewBuilder withReviewId(long reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public ReviewBuilder withAccountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        public ReviewBuilder withCreatureId(long creatureId) {
            this.creatureId = creatureId;
            return this;
        }

        public ReviewBuilder withRating(int rating) {
            this.rating = rating;
            return this;
        }

        public ReviewBuilder withAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public ReviewBuilder withAccountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public ReviewBuilder withCreatureName(String creatureName) {
            this.creatureName = creatureName;
            return this;
        }

        public ReviewBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public ReviewBuilder withDate(Date date) {
            this.date = date;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }

    private Review(ReviewBuilder reviewBuilder) {
        reviewId = reviewBuilder.reviewId;
        accountId = reviewBuilder.accountId;
        creatureId = reviewBuilder.creatureId;
        rating = reviewBuilder.rating;
        avatar = reviewBuilder.avatar;
        accountName = reviewBuilder.accountName;
        creatureName = reviewBuilder.creatureName;
        text = reviewBuilder.text;
        date = reviewBuilder.date;
    }

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

    public String getCreatureName() {
        return creatureName;
    }

    public void setCreatureName(String creatureName) {
        this.creatureName = creatureName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        Review review = (Review) o;
        return reviewId == review.reviewId && accountId == review.accountId && creatureId == review.creatureId &&
                text.equals(review.text) && date.equals(review.date) && rating == review.rating &&
                accountName.equals(review.accountName) && creatureName.equals(review.creatureName) && avatar.equals(review.avatar);
    }

    @Override
    public int hashCode() {
        int hash = Long.hashCode(reviewId);
        hash = hash * 31 + Long.hashCode(accountId);
        hash = hash * 31 + Long.hashCode(creatureId);
        hash = hash * 31 + (text == null ? 0 : text.hashCode());
        hash = hash * 31 + (date == null ? 0 : date.hashCode());
        hash = hash * 31 + rating;
        hash = hash * 31 + (accountName == null ? 0 : accountName.hashCode());
        hash = hash * 31 + (creatureName == null ? 0 : creatureName.hashCode());
        hash = hash * 31 + (avatar == null ? 0 : avatar.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Review{");
        sb.append("reviewId=").append(reviewId);
        sb.append(", accountId=").append(accountId);
        sb.append(", creatureId=").append(creatureId);
        sb.append(", rating=").append(rating);
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append(", accountName='").append(accountName).append('\'');
        sb.append(", creatureName='").append(creatureName).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}
