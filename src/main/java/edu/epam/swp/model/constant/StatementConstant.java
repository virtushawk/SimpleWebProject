package edu.epam.swp.model.constant;

public class StatementConstant {

    private StatementConstant() {}

    public static final String INSERT_USER = "INSERT INTO users(user_id,email,username,password) VALUES(?,?,?,?)";
    public static final String SELECT_USER_BY_EMAIL_PASSWORD = "SELECT user_id,email,username,password FROM users " +
            "WHERE email=? AND password=?";
}
