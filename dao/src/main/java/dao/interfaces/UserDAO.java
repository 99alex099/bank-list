package dao.interfaces;

import entities.User;

public interface UserDAO extends CrudDAO<User> {
    User findUserWithMaxSumAccounts();
}
