package controllers;

import entities.User;
import services.UserService;
import io.javalin.config.JavalinConfig;

public class UserController {

    private static UserService userService = new UserService();

    public static void setRoutes(JavalinConfig config) {

        config.routes.post("/login", ctx -> {
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
        });

        //dashboard ruten tjekker om der er en bruger der er logget ind og hvis der er sendes man videre.
        config.routes.get("/dashboard", ctx -> {
            User user = ctx.sessionAttribute("user");
            if(user != null){
                ctx.attribute("user", user);
                ctx.render("dashboard.html");
                //hvis ikke der er en bruger logget ind sendes man tilbage til login siden
            }else {
                ctx.redirect("/login.html");
            }
        });
    }
}