package controllers;

import io.javalin.Javalin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.PokemonCardService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;

import static org.junit.jupiter.api.Assertions.*;

class PokemonCardControllerTest {
    private Javalin app;
    private final HttpClient client = HttpClient.newHttpClient();
    private int upstreamStatus;
    private boolean timeout;

    @BeforeEach
    void startApp() {
        PokemonCardService service = new PokemonCardService() {
            @Override
            public String search(String language, String query, String field, int page, boolean onlyWithImages) throws IOException {
                if (timeout) throw new HttpTimeoutException("timeout");
                if (upstreamStatus != 0) throw new ApiException(upstreamStatus);
                return "[]";
            }

            @Override
            public String card(String language, String id) throws IOException {
                throw new ApiException(404);
            }
        };
        app = Javalin.create(config -> PokemonCardController.setRoutes(config, service)).start(0);
    }

    @AfterEach
    void stopApp() {
        app.stop();
    }

    private HttpResponse<String> get(String path) throws Exception {
        return client.send(HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + app.port() + path)).build(),
                HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void guestsCanSearchAndReceiveJson() throws Exception {
        var response = get("/api/pokemon/cards?q=Pikachu");
        assertEquals(200, response.statusCode());
        assertEquals("[]", response.body());
        assertTrue(response.headers().firstValue("Content-Type").orElseThrow().contains("application/json"));
    }

    @Test
    void invalidPageIsBadRequest() throws Exception {
        assertEquals(400, get("/api/pokemon/cards?page=abc").statusCode());
    }

    @Test
    void upstreamFailureIsNotAnEmptySearchResult() throws Exception {
        upstreamStatus = 429;
        var response = get("/api/pokemon/cards");
        assertEquals(502, response.statusCode());
        assertEquals("no-store", response.headers().firstValue("Cache-Control").orElseThrow());
    }

    @Test
    void timeoutReturnsGatewayTimeout() throws Exception {
        timeout = true;
        assertEquals(504, get("/api/pokemon/cards").statusCode());
    }

    @Test
    void missingCardReturnsNotFound() throws Exception {
        assertEquals(404, get("/api/pokemon/cards/missing-1").statusCode());
    }
}
