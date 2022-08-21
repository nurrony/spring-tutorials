package info.nmrony.spring.tutorials.security_rbac.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Nur Rony
 */
public class HttpClientUtils {
    private final static HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    public static <T> T getJson(final String url, final Class<T> responseType, final Map<String, String> headersMap)
            throws Exception {

        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url));
        if (!headersMap.isEmpty()) {
            headersMap.entrySet().forEach(entry -> requestBuilder.header(entry.getKey(), entry.getValue()));
        }
        final HttpRequest request = requestBuilder.header("Content-Type", "application/json; charset: UTF-8").build();
        return httpClient.send(request, new JsonResponseBodyHandler<>(responseType)).body().get();
    }

    public static <T> T postJson(final String url, final String json, final Class<T> responseType,
            final Map<String, String> headersMap) throws Exception {

        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(json));
        if (!headersMap.isEmpty()) {
            headersMap.entrySet().forEach(entry -> requestBuilder.header(entry.getKey(), entry.getValue()));
        }
        final HttpRequest request = requestBuilder.header("Content-Type", "application/json; charset: UTF-8").build();
        return httpClient.send(request, new JsonResponseBodyHandler<>(responseType)).body().get();
    }

    public static <T> CompletableFuture<T> getJsonAsync(final String url, final Class<T> responseType,
            final Map<String, String> headersMap) throws Exception {
        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url));
        if (!headersMap.isEmpty()) {
            headersMap.entrySet().forEach(entry -> requestBuilder.header(entry.getKey(), entry.getValue()));
        }
        final HttpRequest request = requestBuilder.header("Content-Type", "application/json; charset: UTF-8").build();
        return httpClient.sendAsync(request, new JsonResponseBodyHandler<>(responseType))
                .thenApply(response -> response.body().get());
    }

    public static <T> CompletableFuture<T> postJsonAsync(final String url, final String json,
            final Class<T> responseType, final Map<String, String> headersMap) throws Exception {
        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(json));
        if (!headersMap.isEmpty()) {
            headersMap.entrySet().forEach(entry -> requestBuilder.header(entry.getKey(), entry.getValue()));
        }
        final HttpRequest request = requestBuilder.header("Content-Type", "application/json; charset: UTF-8").build();
        return httpClient.sendAsync(request, new JsonResponseBodyHandler<>(responseType))
                .thenApply(response -> response.body().get());
    }
}
