package factories;

import entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {

//En liste af testbrugere til at logge ind med indtil databasen kommer
    public static List<User> createUsers() {
        List<User> users = new ArrayList<>();
//Brugere oprettes
        users.add(new User("kristina", "password1"));
        users.add(new User("sofus", "password2"));
        users.add(new User("test", "test1234"));

        return users;
    }
}