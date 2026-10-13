package controllers;

import entities.User;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class PageController {

    public static void setRoutes(JavalinConfig config) {
        // sideruter kommer her
        //dashboard ruten tjekker om der er en bruger der er logget ind og hvis der er sendes man videre.
        config.routes.get("/dashboard", ctx -> {
            User user = ctx.sessionAttribute("user");
            if (user != null) {
                ctx.attribute("user", user);
                ctx.render("dashboard.html");
            } else {
                //hvis ikke der er en bruger logget ind sendes man tilbage til login siden
                ctx.redirect("/login");
            }
        });
        config.routes.get("/collection", ctx -> collection(ctx));
        config.routes.get("/mastersets", ctx -> mastersets(ctx));
        config.routes.get("/wishlist", ctx -> wishlist(ctx));
        config.routes.get("/cards", ctx -> {
            ctx.render("cards.html");
        });
        config.routes.get("/profile", ctx -> profile(ctx));
    }
    public static void collection(Context ctx){
        User user = ctx.sessionAttribute("user");
        if (user != null) {
            ctx.attribute("user", user);
            ctx.render("collection.html");
        }else{
            ctx.redirect("/login.html");
        }
    }
    public static void mastersets(Context ctx){
        User user = ctx.sessionAttribute("user");
        if (user != null) {
            ctx.attribute("user", user);
            ctx.render("mastersets.html");
        }else{
            ctx.redirect("/login.html");
        }
    }
    public static void wishlist(Context ctx){
        User user = ctx.sessionAttribute("user");
        if (user != null) {
            ctx.attribute("user", user);
            ctx.render("wishlist.html");
        }else{
            ctx.redirect("/login.html");
        }
    }
    public static void profile(Context ctx){
        User user = ctx.sessionAttribute("user");
        if(user != null){
            ctx.attribute("user", user);
            ctx.render("profile.html");
        }else{
            ctx.redirect("/login");
        }
    }
}
