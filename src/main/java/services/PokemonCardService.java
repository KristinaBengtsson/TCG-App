package services;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Set;

/** Læser kort fra TCGdex. Ingen API-nøgle er nødvendig. */
public class PokemonCardService {
    public static final int PAGE_SIZE = 24;
    private static final Set<String> LANGUAGES = Set.of(
            "en", "fr", "es", "it", "pt", "pt-br", "pt-pt", "de", "nl", "pl", "ru",
            "ja", "ko", "zh-tw", "zh-cn", "id", "th");
    private final HttpClient client;
    private final URI baseUri;
    private final Duration timeout;

    public PokemonCardService() {
        this(HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build(),
                URI.create("https://api.tcgdex.net/v2/"), Duration.ofSeconds(15));
    }

    // Gør det muligt at teste mod en lokal HTTP-server.
    PokemonCardService(HttpClient client, URI baseUri, Duration timeout) {
        this.client = client;
        this.baseUri = baseUri;
        this.timeout = timeout;
    }

    public String search(String language, String query, String field, int page)
            throws IOException, InterruptedException {
        return search(language, query, field, page, false);
    }

    public String search(String language, String query, String field, int page, boolean onlyWithImages)
            throws IOException, InterruptedException {
        validateLanguage(language);
        if (!Set.of("name", "id", "localId", "set.name", "illustrator", "dexId").contains(field)) {
            throw new IllegalArgumentException("Invalid search field.");
        }
        if (page < 1 || page > 10000) {
            throw new IllegalArgumentException("Page must be between 1 and 10000.");
        }
        String term = query == null ? "" : query.strip();
        if (term.length() > 100) {
            throw new IllegalArgumentException("The search must be 100 characters or fewer.");
        }
        if (field.equals("dexId") && !term.isEmpty() && !term.matches("[0-9]{1,4}")) {
            throw new IllegalArgumentException("Pokédex number must contain up to 4 digits.");
        }
        String path = language + "/cards?pagination:page=" + page
                + "&pagination:itemsPerPage=" + PAGE_SIZE + "&sort:field=id&sort:order=ASC";
        if (onlyWithImages) {
            path += "&image=notnull:";
        }
        if (!term.isEmpty()) {
            // Et fast præfiks forhindrer, at fx 'not:' tolkes som en anden filtertype.
            String filter = field.equals("dexId") ? "eq:" : "like:";
            path += "&" + field + "=" + URLEncoder.encode(filter + term, StandardCharsets.UTF_8);
        }
        return get(path);
    }

    public String card(String language, String id) throws IOException, InterruptedException {
        validateLanguage(language);
        if (id == null || !id.matches("[A-Za-z0-9][A-Za-z0-9._-]{0,99}")) {
            throw new IllegalArgumentException("Invalid card ID.");
        }
        return get(language + "/cards/" + id);
    }

    private void validateLanguage(String language) {
        if (language == null || !LANGUAGES.contains(language)) {
            throw new IllegalArgumentException("The selected card language is not supported.");
        }
    }

    private String get(String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(baseUri.resolve(path))
                .timeout(timeout).header("Accept", "application/json").GET().build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() != 200) {
            throw new ApiException(response.statusCode());
        }
        if (!response.headers().firstValue("Content-Type").orElse("").contains("application/json")) {
            throw new IOException("TCGdex did not return JSON.");
        }
        return response.body();
    }

    public static class ApiException extends IOException {
        private final int statusCode;

        public ApiException(int statusCode) {
            super("TCGdex HTTP " + statusCode);
            this.statusCode = statusCode;
        }

        public int statusCode() {
            return statusCode;
        }
    }
}
