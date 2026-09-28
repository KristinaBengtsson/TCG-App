import controllers.UserController;
import controllers.PageController;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class Main {
    public static void main(String[] args) {
        var templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        var templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        //Opretter Javalin appen
        var app = Javalin.create(config -> {
            //Lader controllen registrere sine egne routes
            UserController.setRoutes(config);
            PageController.setRoutes(config);
            //Arkiverer statiske filer
            config.staticFiles.add("/public"); //til login.html, style.css, js/
            config.fileRenderer(new JavalinThymeleaf(templateEngine)); //til dashboard.html
       //Starter serven på port  (7070)
        }).start(7070);
    }
}