package info.nmrony.spring.tutorials.springplayground.utils;

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

    public static <T> T getJson(String url, Class<T> responseType, Map<String, String> headersMap) throws Exception {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url));
        if (!headersMap.isEmpty()) {
            headersMap.entrySet().forEach((entry) -> requestBuilder.header(entry.getKey(), entry.getValue()));
        }
        HttpRequest request = requestBuilder.header("Content-Type", "application/json; charset: UTF-8").build();
        return httpClient.send(request, new JsonResponseBodyHandler<>(responseType)).body().get();
    }

    public static <T> T postJson(String url, String json, Class<T> responseType, Map<String, String> headersMap)
            throws Exception {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(json));
        if (!headersMap.isEmpty()) {
            headersMap.entrySet().forEach((entry) -> requestBuilder.header(entry.getKey(), entry.getValue()));
        }
        HttpRequest request = requestBuilder.header("Content-Type", "application/json; charset: UTF-8").build();
        return httpClient.send(request, new JsonResponseBodyHandler<>(responseType)).body().get();
    }

    public static <T> CompletableFuture<T> getJsonAsync(String url, Class<T> responseType,
            Map<String, String> headersMap) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url));
        if (!headersMap.isEmpty()) {
            headersMap.entrySet().forEach((entry) -> requestBuilder.header(entry.getKey(), entry.getValue()));
        }
        HttpRequest request = requestBuilder.header("Content-Type", "application/json; charset: UTF-8").build();
        return httpClient.sendAsync(request, new JsonResponseBodyHandler<>(responseType))
                .thenApply((response) -> response.body().get());
    }

    public static <T> CompletableFuture<T> postJsonAsync(String url, String json, Class<T> responseType,
            Map<String, String> headersMap) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(json));
        if (!headersMap.isEmpty()) {
            headersMap.entrySet().forEach((entry) -> requestBuilder.header(entry.getKey(), entry.getValue()));
        }
        HttpRequest request = requestBuilder.header("Content-Type", "application/json; charset: UTF-8").build();
        return httpClient.sendAsync(request, new JsonResponseBodyHandler<>(responseType))
                .thenApply((response) -> response.body().get());
    }
}
