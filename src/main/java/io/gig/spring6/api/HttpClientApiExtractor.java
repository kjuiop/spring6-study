package io.gig.spring6.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author : JAKE
 * @date : 2025/06/10
 */
public class HttpClientApiExtractor implements ApiExecutor {

    @Override
    public String execute(URI uri) throws IOException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
