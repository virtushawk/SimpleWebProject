package edu.epam.swp.model.constant;

public class StatementConstant {

    private StatementConstant() {}

    public static final String INSERT_ACCOUNT = "INSERT INTO accounts(account_id,email,username,password,role_id) " +
            "VALUES(?,?,?,?,?)";
    public static final String SELECT_ACCOUNT_BY_USERNAME_PASSWORD =
            "SELECT accounts.account_id,accounts.email,accounts.username,roles.role " +
            "FROM accounts INNER JOIN roles ON accounts.role_id = roles.role_id " +
            "WHERE accounts.username =? AND accounts.password =?";
}
