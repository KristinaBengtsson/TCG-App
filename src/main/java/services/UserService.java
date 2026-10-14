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

    public void createUser(String username, String email, String password1, String password2) throws DatabaseException {
        //Reglen hører til i servicen: de to passwords skal være ens
        if (!password1.equals(password2)) {
            throw new DatabaseException("De to passwords er ikke ens");
        }
        userMapper.createUser(username, email, password1);
    }
}