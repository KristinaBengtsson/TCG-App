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
        config.routes.get("/login", ctx -> ctx.render("login.html"));
        config.routes.post("/login", ctx -> login(ctx));
        config.routes.get("/createuser", ctx -> ctx.render("createuser.html"));
        config.routes.post("/createuser", ctx -> createUser(ctx));
        config.routes.get("/logout", ctx -> logout(ctx));
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
                //Viser login-siden igen, nu med en fejlbesked
                ctx.attribute("error", "Forkert brugernavn eller adgangskode");
                ctx.render("login.html");
            }
    }

    public void createUser(Context ctx) {
        String username = ctx.formParam("username");
        String email = ctx.formParam("email");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        try {
            userService.createUser(username, email, password1, password2);
            //Det lykkedes: vis login-siden med en besked
            ctx.attribute("message", "Din bruger er oprettet. Du kan nu logge ind.");
            ctx.render("login.html");
        } catch (DatabaseException e) {
            //Det gik galt: vis formularen igen med fejlbeskeden
            ctx.attribute("error", e.getMessage());
            ctx.render("createuser.html");
        }
    }

    public void logout(Context ctx) {
        //Sletter hele sessionen, så brugeren ikke længere er gemt
        ctx.req().getSession().invalidate();
        ctx.redirect("/login");
    }
}