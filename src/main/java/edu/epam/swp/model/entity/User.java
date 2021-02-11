package edu.epam.swp.model.entity;

import java.io.Serializable;

public class User implements Serializable {
    private long id;
    private String email;
    private String username;
    private String avatar;
    private AccountRole role;

    //todo builder


    public User(AccountRole accountRole) {
        this.role = accountRole;
    }

    public User(String email,String username,String avatar) {
        this.email = email;
        this.username = username;
        this.avatar = avatar;
    }

    public User(String email, String username,AccountRole role,String avatar) {
        this.email = email;
        this.username = username;
        this.role = role;
        this.avatar = avatar;
    }

    public User(String email, String username,AccountRole role) {
        this.email = email;
        this.username = username;
        this.role = role;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email) && username.equals(user.username) &&
                role.equals(user.role) && avatar.equals(user.avatar);
    }

    @Override
    public int hashCode() {
        int hash = email.hashCode();
        hash = hash * 31 + username.hashCode();
        hash = hash * 31 + role.hashCode();
        hash = hash * 31 + avatar.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("email='").append(email).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", role='").append(role).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
