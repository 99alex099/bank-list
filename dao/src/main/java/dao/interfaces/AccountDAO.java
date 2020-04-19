package dao.interfaces;

import entities.Account;
import entities.User;

import java.util.List;

public interface AccountDAO extends CrudDAO<Account> {
    List<Account> findAccountsByUser(User user);
    List<Account> findTotalSumOfUserAccount();
}
