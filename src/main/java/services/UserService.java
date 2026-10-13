package services;

import entities.User;
import persistence.DatabaseException;
import persistence.UserMapper;

//UserService indeholder logikken om brugere (regler og tjek).
//Selve SQL'en ligger i UserMapper.
public class UserService {

    private UserMapper userMapper;

    //UserMapper bliver givet med udefra (Dependency Injection)
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User login(String username, String password) throws DatabaseException {
        return userMapper.login(username, password);
    }
}