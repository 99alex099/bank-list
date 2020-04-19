package entities;

public class Account {
    private int accountId;
    private int account;
    private User user;

    public Account() {
    }

    public Account(int accountId, int account, User user) {
        this.accountId = accountId;
        this.account = account;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", account=" + account +
                ", user=" + user +
                '}';
    }

    public Account(int account, User user) {
        this.account = account;
        this.user = user;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
