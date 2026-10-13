import controllers.UserController;
import controllers.PageController;
import controllers.PokemonCardController;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import persistence.ConnectionPool;
import persistence.UserMapper;
import services.UserService;

public class Main {
    //Databaseindstillinger. Skal kun ændres her ét sted.
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s";
    private static final String DB = "tcg";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    public static void main(String[] args) {
        var templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        var templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        //Dependency Injection: vi bygger objekterne her og giver dem videre gennem konstruktørerne.
        //Mappers får connectionPool -> services får mappers -> controllers får services.
        UserMapper userMapper = new UserMapper(connectionPool);
        UserService userService = new UserService(userMapper);
        UserController userController = new UserController(userService);

        //Opretter Javalin appen
        var app = Javalin.create(config -> {
            //Lader controllen registrere sine egne routes
            userController.setRoutes(config); //objektet, ikke klassen
            PageController.setRoutes(config);
            PokemonCardController.setRoutes(config);
            //Arkiverer statiske filer
            config.staticFiles.add("/public"); //til login.html, style.css, js/
            config.fileRenderer(new JavalinThymeleaf(templateEngine)); //til dashboard.html
       //Starter serven på port  (7070)
        }).start(7070);
    }
}
