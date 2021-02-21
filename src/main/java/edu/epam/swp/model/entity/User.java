package edu.epam.swp.model.entity;

import java.io.Serializable;

public class User implements Serializable {

    private long accountId;
    private String email;
    private String username;
    private String avatar;
    private AccountRole role;
    private String name;

    public static class UserBuilder {

        private long accountId;
        private String email;
        private String username;
        private String avatar;
        private AccountRole role;
        private String name;

        public UserBuilder() {}

        public UserBuilder withAccountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder withAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public UserBuilder withRole(AccountRole role) {
            this.role = role;
            return this;
        }

        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    private User(UserBuilder userBuilder) {
        accountId = userBuilder.accountId;
        email = userBuilder.email;
        username = userBuilder.username;
        avatar = userBuilder.avatar;
        role = userBuilder.role;
        name = userBuilder.name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return accountId == user.accountId && email.equals(user.email) && username.equals(user.username) &&
                role.equals(user.role) && avatar.equals(user.avatar) && name.equals(user.name);
    }

    @Override
    public int hashCode() {
        int hash = Long.hashCode(accountId);
        hash = hash * 31 + (email == null ? 0 : email.hashCode());
        hash = hash * 31 + (username == null ? 0 : username.hashCode());
        hash = hash * 31 + (role == null ? 0 : role.hashCode());
        hash = hash * 31 + (avatar == null ? 0 : avatar.hashCode());
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
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
        sb.append('}');
        return sb.toString();
    }
}
