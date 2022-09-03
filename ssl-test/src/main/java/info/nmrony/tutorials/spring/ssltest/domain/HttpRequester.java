package info.nmrony.tutorials.spring.ssltest.domain;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @author Nur Rony
 */

public class HttpRequester {

    private final HttpClient httpClient;

    public HttpRequester(boolean verifySSL) throws KeyManagementException, NoSuchAlgorithmException {
        httpClient = verifySSL ? buildSecureHttp2Client() : buildInsecureHttp2Client();
    }

    private HttpClient buildSecureHttp2Client() {
        return HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    }

    private HttpClient buildInsecureHttp2Client() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, getSelfSignedTrustManagers(), new SecureRandom());
        return HttpClient.newBuilder()
                .sslContext(sslContext)
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public <T> T getJson(final String url, final Class<T> responseType, final Map<String, String> headersMap)
            throws Exception {

        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url));
        if (!headersMap.isEmpty()) {
            headersMap.entrySet().forEach(entry -> requestBuilder.header(entry.getKey(), entry.getValue()));
        }
        final HttpRequest request = requestBuilder.header("Content-Type", "application/json; charset: UTF-8").build();
        return httpClient.send(request, new JsonResponseBodyHandler<>(responseType)).body().get();
    }

    public <T> T postJson(final String url, final String json, final Class<T> responseType,
            final Map<String, String> headersMap) throws Exception {

        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(json));
        if (!headersMap.isEmpty()) {
            headersMap.entrySet().forEach(entry -> requestBuilder.header(entry.getKey(), entry.getValue()));
        }
        final HttpRequest request = requestBuilder.header("Content-Type", "application/json; charset: UTF-8").build();
        return httpClient.send(request, new JsonResponseBodyHandler<>(responseType)).body().get();
    }

    public <T> CompletableFuture<T> getJsonAsync(final String url, final Class<T> responseType,
            final Map<String, String> headersMap) throws Exception {
        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url));
        if (!headersMap.isEmpty()) {
            headersMap.entrySet().forEach(entry -> requestBuilder.header(entry.getKey(), entry.getValue()));
        }
        final HttpRequest request = requestBuilder.header("Content-Type", "application/json; charset: UTF-8").build();
        return httpClient.sendAsync(request, new JsonResponseBodyHandler<>(responseType))
                .thenApply(response -> response.body().get());
    }

    public <T> CompletableFuture<T> postJsonAsync(final String url, final String json,
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

    private TrustManager[] getSelfSignedTrustManagers() {
        return new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
    }
}
