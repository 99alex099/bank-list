package services.interfaces;

import entities.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
}
