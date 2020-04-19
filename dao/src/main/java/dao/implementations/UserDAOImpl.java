package dao.implementations;

import dao.interfaces.UserDAO;
import entities.User;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAOImpl extends CrudDAOImpl<User> implements UserDAO {

    private static final String QUERY_FIND_USER_WITH_MAX_ACCOUNT_SUM =
            "SELECT `userId`, max(sum) FROM (SELECT `users`.`userId`, sum(account) as sum FROM `accountdata`.`accounts` \n" +
                    "JOIN `accountdata`.`users` ON `accounts`.`userId` = `users`.`userId`\n" +
                    "GROUP BY `users`.`userId`) userSum";

    protected String getTableName() {
        return "users";
    }

    protected String formSelectQuery(String condition) {
        return "SELECT * FROM `users` WHERE " + condition;
    }

    protected String formInsertQuery(User user) {
        return "INSERT INTO `users` " +
                "(`name`, `surename`)" +
                " VALUES " +
                "(" +
                "'" + user.getName() + "', " +
                "'" + user.getSureName() + "' " +
                ")";
    }

    protected String formUpdateQuery(User entity) {
        return "UPDATE `users` SET " +
                "`name` = '" + entity.getName() + "', " +
                "`surename` = '" + entity.getSureName() + "'" +
                " WHERE `userId` = " + entity.getUserId();
    }

    protected User createEntity(ResultSet resultSet) throws SQLException {
        try {
            return new User(
                    resultSet.getInt("userId"),
                    resultSet.getString("name"),
                    resultSet.getString("surename"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String getIdName() {
        return "userId";
    }

    protected void setEntityId(User user, int newId) {
        user.setUserId(newId);
    }

    protected Integer getEntityId(User user) {
        return user.getUserId();
    }

    @Override
    public User findUserWithMaxSumAccounts() {

        try {
            ResultSet resultSet = ConnecterSQL.getConnecter().getStatement().executeQuery(
                    QUERY_FIND_USER_WITH_MAX_ACCOUNT_SUM
            );

            if (resultSet.next()) {
                return createEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
