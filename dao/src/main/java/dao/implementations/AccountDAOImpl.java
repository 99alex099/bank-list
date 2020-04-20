package dao.implementations;

import dao.interfaces.AccountDAO;
import entities.Account;
import entities.User;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

//фасад
//в чём фишка dao

@Repository
public class AccountDAOImpl extends CrudDAOImpl<Account> implements AccountDAO {
    private static final String QUERY_FIND_TOTAL_SUM_OF_ACCOUNTS =
            "(SELECT `users`.`userId`, `name`, `surename`, sum(account) " +
            "as sum FROM `accountdata`.`accounts` " +
            "JOIN `accountdata`.`users` ON `accounts`.`userId` = `users`.`userId` " +
            "GROUP BY `userId`)";

    protected String getTableName() {
        return "accounts";
    }

    protected String formSelectQuery(String condition) {
        return "SELECT * FROM `accounts` JOIN `users` ON `accounts`.`userId` = `users`.`userId` " +
                "WHERE " + condition;
    }

    protected String formInsertQuery(Account account) {
        return "INSERT INTO `accounts` " +
                "(`account`, `userId`)" +
                " VALUES " +
                "(" +
                account.getAccount() + ", " +
                account.getUser().getUserId() + " " +
                ")";
    }

    protected String formUpdateQuery(Account account) {
        return "UPDATE `account` SET " +
                "`account` = " + account.getAccount() + ", " +
                "`userId` = " + account.getUser().getUserId() + "" +
                " WHERE `accountId` = " + account.getAccountId();
    }

    protected Account createEntity(ResultSet resultSet) throws SQLException {
        try {
            final User OWNER = new User(
                    resultSet.getInt("userId"),
                    resultSet.getString("name"),
                    resultSet.getString("surename"));

            return new Account(
                    resultSet.getInt("accountId"),
                    resultSet.getInt("account"),
                    OWNER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String getIdName() {
        return "accountId";
    }

    protected void setEntityId(Account account, int newId) {
        account.setAccountId(newId);
    }

    protected Integer getEntityId(Account entity) {
        return entity.getAccountId();
    }

    public List<Account> findAccountsByUser(User user) {
        List<Account> list = new LinkedList<>();

        try {

            ResultSet resultSet = ConnecterSQL.getConnecter().getStatement().executeQuery(
                    formSelectQuery("`accounts`.`userId` = " + user.getUserId())
            );

            if (resultSet != null) {
                while (resultSet.next()) {
                    list.add(createEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Account> findTotalSumOfUserAccount() {
        try {
            ResultSet resultSet = ConnecterSQL.getConnecter().getStatement().executeQuery(
                    QUERY_FIND_TOTAL_SUM_OF_ACCOUNTS);

            List<Account> accounts = new LinkedList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    final User OWNER = new User(
                            resultSet.getInt("userId"),
                            resultSet.getString("name"),
                            resultSet.getString("surename"));
                    accounts.add( new Account(
                            resultSet.getInt("sum"),
                            OWNER));
                }
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}