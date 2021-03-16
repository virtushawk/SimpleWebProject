package edu.epam.swp.model.entity;

import java.io.Serializable;

/**
 * The User class is used to encapsulate all data needed to work with users.
 * @author romab
 */
public class User implements Serializable {

    private long accountId;
    private String email;
    private String username;
    private String avatar;
    private AccountRole role;
    private String name;
    private int numberReviews;
    private UserStatus userStatus;

    /**
     * The User builder is used to create user object.
     */
    public static class UserBuilder {

        private long accountId;
        private String email;
        private String username;
        private String avatar;
        private AccountRole role;
        private String name;
        private int numberReviews;
        private UserStatus userStatus;

        /**
         * Instantiates a new User builder.
         */
        public UserBuilder() {}

        /**
         * Adds User's id to the builder.
         * @param accountId User's id.
         * @return UserBuilder object.
         */
        public UserBuilder withAccountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        /**
         * Adds email to the builder.
         * @param email String containing the email.
         * @return UserBuilder object.
         */
        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        /**
         * Adds username to the builder.
         * @param username String containing the username
         * @return UserBuilder object.
         */
        public UserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        /**
         * Adds avatar to the builder.
         * @param avatar String containing the path to the image.
         * @return UserBuilder object.
         */
        public UserBuilder withAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        /**
         * Adds role to the builder.
         * @param role String containing the role.
         * @return UserBuilder object.
         */
        public UserBuilder withRole(AccountRole role) {
            this.role = role;
            return this;
        }

        /**
         * Adds the name to the builder.
         * @param name String containing the name.
         * @return UserBuilder object.
         */
        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Adds the number of reviews to the builder.
         * @param numberReviews number of reviews.
         * @return UserBuilder object.
         */
        public UserBuilder withNumberReviews(int numberReviews) {
            this.numberReviews = numberReviews;
            return this;
        }

        /**
         * Adds user's status to the builder.
         * @param userStatus User's status.
         * @return UserBuilder object.
         */
        public UserBuilder withUserStatus(UserStatus userStatus) {
            this.userStatus = userStatus;
            return this;
        }

        /**
         * Builds user.
         * @return User object
         */
        public User build() {
            return new User(this);
        }
    }

    /**
     * Creates user object with help of UserBuilder.
     * @param userBuilder object.
     */
    private User(UserBuilder userBuilder) {
        accountId = userBuilder.accountId;
        email = userBuilder.email;
        username = userBuilder.username;
        avatar = userBuilder.avatar;
        role = userBuilder.role;
        name = userBuilder.name;
        numberReviews = userBuilder.numberReviews;
        userStatus = userBuilder.userStatus;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberReviews() {
        return numberReviews;
    }

    public void setNumberReviews(int numberReviews) {
        this.numberReviews = numberReviews;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return accountId == user.accountId && email.equals(user.email)  && numberReviews == user.numberReviews
                && (username == null ? user.username == null : username.equals(user.username))
                && (role == null ? user.role == null : role.equals(user.role))
                && (avatar == null ? user.avatar == null : avatar.equals(user.avatar))
                && (name == null ? user.name == null : name.equals(user.name))
                && (userStatus == null ? user.userStatus == null : userStatus.equals(user.userStatus));
    }

    @Override
    public int hashCode() {
        int hash = Long.hashCode(accountId);
        hash = hash * 31 + (email == null ? 0 : email.hashCode());
        hash = hash * 31 + (username == null ? 0 : username.hashCode());
        hash = hash * 31 + (role == null ? 0 : role.hashCode());
        hash = hash * 31 + (avatar == null ? 0 : avatar.hashCode());
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        hash = hash * 31 + (userStatus == null ? 0 : userStatus.hashCode());
        hash = hash * 31 + numberReviews;
        return hash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("accountId=").append(accountId);
        sb.append(", email='").append(email).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append(", role=").append(role);
        sb.append(", name='").append(name).append('\'');
        sb.append(", numberReviews=").append(numberReviews);
        sb.append(", userStatus=").append(userStatus);
        sb.append('}');
        return sb.toString();
    }
}
