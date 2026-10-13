package controllers;

import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import services.PokemonCardService;

import java.io.IOException;
import java.net.http.HttpTimeoutException;

public class PokemonCardController {
    public static void setRoutes(JavalinConfig config) {
        setRoutes(config, new PokemonCardService());
    }

    public static void setRoutes(JavalinConfig config, PokemonCardService service) {
        config.routes.get("/api/pokemon/cards", ctx -> respond(ctx, () -> {
            String page = parameter(ctx, "page", "1");
            if (!page.matches("[0-9]{1,5}")) {
                throw new IllegalArgumentException("Ugyldigt sidenummer.");
            }
            return service.search(parameter(ctx, "language", "en"), ctx.queryParam("q"),
                    parameter(ctx, "field", "name"), Integer.parseInt(page));
        }));
        config.routes.get("/api/pokemon/cards/{id}", ctx -> respond(ctx,
                () -> service.card(parameter(ctx, "language", "en"), ctx.pathParam("id"))));
    }

    private static String parameter(Context ctx, String name, String fallback) {
        String value = ctx.queryParam(name);
        return value == null ? fallback : value;
    }

    private static void respond(Context ctx, ApiCall call) {
        ctx.header("Cache-Control", "no-store");
        try {
            String json = call.execute();
            ctx.header("Cache-Control", "private, max-age=300")
                    .contentType("application/json; charset=utf-8").result(json);
        } catch (IllegalArgumentException e) {
            error(ctx, 400, e.getMessage());
        } catch (HttpTimeoutException e) {
            error(ctx, 504, "Kortdatabasen svarer for langsomt. Prøv igen.");
        } catch (PokemonCardService.ApiException e) {
            if (e.statusCode() == 404) {
                error(ctx, 404, "Kortet blev ikke fundet på det valgte sprog.");
            } else {
                error(ctx, 502, "Kortdatabasen er midlertidigt utilgængelig. Prøv igen om lidt.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            error(ctx, 503, "Søgningen blev afbrudt. Prøv igen.");
        } catch (IOException e) {
            error(ctx, 502, "Kunne ikke hente kort fra kortdatabasen. Prøv igen.");
        }
    }

    private static void error(Context ctx, int status, String message) {
        ctx.status(status).contentType("text/plain; charset=utf-8").result(message);
    }

    @FunctionalInterface
    private interface ApiCall {
        String execute() throws IOException, InterruptedException;
    }
}
