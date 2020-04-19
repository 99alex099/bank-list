package services.interfaces;

import dto.TotalAccount;
import entities.Account;
import entities.User;

import java.util.List;

public interface AccountService {
    TotalAccount findMaxTotalAccountSum();
    List<Account> findUserAccounts(User user);
}
