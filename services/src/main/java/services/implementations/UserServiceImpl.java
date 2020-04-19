package services.implementations;

import dao.implementations.UserDAOImpl;
import dao.interfaces.UserDAO;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import services.interfaces.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
    }

    public List<User> findAllUsers() {
        return userDAO.findAll();
    }
}
