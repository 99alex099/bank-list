package dto;

public class TotalAccount {
    private int sum;
    private UserDTO owner;

    public TotalAccount(int sum, UserDTO owner) {
        this.sum = sum;
        this.owner = owner;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "TotalAccount{" +
                "sum=" + sum +
                ", owner=" + owner +
                '}';
    }
}
