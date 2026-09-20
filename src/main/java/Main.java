import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import controllers.UserController;

public class Main {
    public static void main(String[] args) {
//Opret et nyt resolver-objekt "Givet et filnavn, find den fil
        var templateResolver = new ClassLoaderTemplateResolver();
        //uanset hvilket navn du får, læg altid templates/ foran
        templateResolver.setPrefix("templates/");
        //filerne skal læses som html
        templateResolver.setTemplateMode(TemplateMode.HTML);
        //sikre at danske bogstaver læses korrekt fra filerne
        templateResolver.setCharacterEncoding("UTF-8");

        //Opretter Thymelead-motoren og fortæller den "brug den resolber jeg lavede ovenfor til at finde dine filer.
        var templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        //Opretter Javalin appen
        var app = Javalin.create(config -> {
            //Lader controllen registrere sine egne routes
            UserController.setRoutes(config);
            //Arkiverer statiske filer
            config.staticFiles.add("/public"); //til login.html, style.css, js/
            //Fortæller Javalin at når nogen kalder ctx.render(...) skal den bruge Thymeleaf til det
            config.fileRenderer(new JavalinThymeleaf(templateEngine)); //til dashboard.html
       //Starter serven på port 7070
        }).start(7070);
    }
}