package dto;

public class UserDTO {
    private int userId;
    private String name;
    private String surname;

    public UserDTO() {
    }

    public UserDTO(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public UserDTO(int userId, String name, String surname) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    @Override
    public String toString() {
        return getName() + "-" + getSurname();
    }
}
