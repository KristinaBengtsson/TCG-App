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
                throw new IllegalArgumentException("Invalid page number.");
            }
            return service.search(parameter(ctx, "language", "en"), ctx.queryParam("q"),
                    parameter(ctx, "field", "name"), Integer.parseInt(page),
                    "true".equals(ctx.queryParam("onlyWithImages")));
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
            error(ctx, 504, "The card database timed out. Please try again.");
        } catch (PokemonCardService.ApiException e) {
            if (e.statusCode() == 404) {
                error(ctx, 404, "The card was not found in the selected language.");
            } else {
                error(ctx, 502, "The card database is temporarily unavailable. Please try again shortly.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            error(ctx, 503, "The search was interrupted. Please try again.");
        } catch (IOException e) {
            error(ctx, 502, "Could not load cards from the card database. Please try again.");
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
