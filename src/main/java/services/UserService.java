package services;

import entities.User;
import factories.UserFactory;

import java.util.List;

public class UserService {
//Listen af brugerer
    private List<User> users;

//Opretter UserService og henter listen af testbruger fra USerFactory
    public UserService() {
        this.users = UserFactory.createUsers();
    }

//Går gennem listen af brugerer og leder efter en bruger hvor både brugernavn og password matcher
    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}