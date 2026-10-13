package services;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class PokemonCardServiceTest {
    private HttpServer server;
    private PokemonCardService service;
    private volatile String requestedUri;
    private volatile int status = 200;
    private volatile String body = "[]";
    private volatile String contentType = "application/json; charset=utf-8";
    private volatile long delay;

    @BeforeEach
    void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/v2/", exchange -> {
            requestedUri = exchange.getRequestURI().toString();
            try {
                Thread.sleep(delay);
                byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(status, bytes.length);
                exchange.getResponseBody().write(bytes);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                exchange.close();
            }
        });
        server.start();
        service = new PokemonCardService(HttpClient.newHttpClient(), baseUri(), Duration.ofSeconds(2));
    }

    private URI baseUri() {
        return URI.create("http://127.0.0.1:" + server.getAddress().getPort() + "/v2/");
    }

    @AfterEach
    void stopServer() {
        server.stop(0);
    }

    @Test
    void encodesUnicodeAndQuerySeparatorsWithoutInjectingParameters() throws Exception {
        body = "[{\"id\":\"test-1\",\"name\":\"ピカチュウ\"}]";
        assertEquals(body, service.search("ja", "ピカチュウ &name=not:foo", "name", 2));
        assertTrue(requestedUri.startsWith("/v2/ja/cards?"));
        assertTrue(requestedUri.contains("pagination:page=2&pagination:itemsPerPage=24"));
        assertTrue(requestedUri.contains("%26name%3Dnot%3Afoo"));
        assertTrue(URLDecoder.decode(requestedUri, StandardCharsets.UTF_8).contains("name=like:ピカチュウ &name=not:foo"));
    }

    @Test
    void emptyQueryBrowsesCatalogAndEmptyResultsRemainValid() throws Exception {
        assertEquals("[]", service.search("en", "  ", "name", 1));
        assertFalse(requestedUri.contains("&name="));
    }

    @Test
    void dexNumberUsesExactMatching() throws Exception {
        service.search("en", "25", "dexId", 1);
        assertTrue(requestedUri.contains("dexId=eq%3A25"));
    }

    @Test
    void rejectsInvalidInputsBeforeMakingRequests() {
        assertThrows(IllegalArgumentException.class, () -> service.search("../../", "", "name", 1));
        assertThrows(IllegalArgumentException.class, () -> service.search("en", "", "unknown", 1));
        assertThrows(IllegalArgumentException.class, () -> service.search("en", "", "name", 0));
        assertThrows(IllegalArgumentException.class, () -> service.search("en", "x".repeat(101), "name", 1));
        assertThrows(IllegalArgumentException.class, () -> service.search("en", "Pikachu", "dexId", 1));
        assertThrows(IllegalArgumentException.class, () -> service.card("en", "../sets"));
        assertNull(requestedUri);
    }

    @Test
    void fetchesCardDetailsInSelectedLanguage() throws Exception {
        body = "{\"id\":\"sv03.5-1\"}";
        assertEquals(body, service.card("en", "sv03.5-1"));
        assertEquals("/v2/en/cards/sv03.5-1", requestedUri);
    }

    @Test
    void preservesUpstreamErrorStatus() {
        status = 429;
        PokemonCardService.ApiException exception = assertThrows(PokemonCardService.ApiException.class,
                () -> service.search("en", "Pikachu", "name", 1));
        assertEquals(429, exception.statusCode());
    }

    @Test
    void rejectsHtmlFromUpstream() {
        contentType = "text/html";
        assertThrows(IOException.class, () -> service.search("en", "", "name", 1));
    }

    @Test
    void timesOutInsteadOfWaitingIndefinitely() {
        delay = 500;
        service = new PokemonCardService(HttpClient.newHttpClient(), baseUri(), Duration.ofMillis(50));
        assertThrows(HttpTimeoutException.class, () -> service.search("en", "", "name", 1));
    }
}
