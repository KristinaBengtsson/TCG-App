package controllers;

import entities.User;
import services.UserService;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import persistence.DatabaseException;

public class UserController {

    private UserService userService;

    //UserService bliver givet med udefra (Dependency Injection) i stedet for at blive lavet her
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void setRoutes(JavalinConfig config){
        config.routes.post("/login", ctx -> login(ctx));
       // config.routes.get("/createuser", ctx -> createUser(ctx));
}

    public void login(Context ctx) throws DatabaseException{
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");

            User user = userService.login(username, password);

            if (user != null) {
                //Sender textbesked om succesfuldt login
                /*ctx.result("Login lykkedes! Velkommen, " + user.getUsername());*/

                //Sender brugeren videre til dashboard ved succesfuldt login
                ctx.sessionAttribute("user", user);
                ctx.redirect("/dashboard");
            } else {
                ctx.status(401).result("Forkert brugernavn eller adgangskode");
            }
    }
}