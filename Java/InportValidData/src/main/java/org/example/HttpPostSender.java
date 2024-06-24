package org.example;
import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HttpPostSender {

    private static final int MAX_REQUESTS_PER_SECOND = 10;
    private static final long DELAY_BETWEEN_REQUESTS_MS = 1000 / MAX_REQUESTS_PER_SECOND;

    private final HttpClient httpClient;
    private final ScheduledExecutorService scheduler;

    public HttpPostSender() {
        this.httpClient = HttpClient.newHttpClient();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public List<CallResult> sendPosts(List<String> strings, String url) {
        List<CompletableFuture<CallResult>> futures = new ArrayList<>();

        for (int i = 0; i < strings.size(); i++) {
            String data = strings.get(i);
            CompletableFuture<CallResult> future = CompletableFuture.supplyAsync(
                    () -> sendPost(data, url),
                    new CustomExecutor(100)
            );
            futures.add(future);
        }

        // Wait for all futures to complete and collect the results
        List<CallResult> results = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        return results;
    }

    private CallResult sendPost(String data, String url) {
        CallResult result = new CallResult();
        result.data = data;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            result.success = response.statusCode() == 200;
            result.responseBody = response.body();
        } catch (Exception e) {
            result.success = false;
            result.error = e.getMessage();
        }
        return result;
    }

    private ScheduledExecutorService getScheduledExecutor(long delay) {
        ScheduledExecutorService delayedExecutor = Executors.newSingleThreadScheduledExecutor();
        delayedExecutor.schedule(() -> {}, delay, TimeUnit.MILLISECONDS);
        return delayedExecutor;
    }

    public void sendList(List<String> strings) {
        HttpPostSender sender = new HttpPostSender();
        String url = "http://localhost:8000";

        List<CallResult> results = sender.sendPosts(strings, url);
        results.forEach(result -> {
            System.out.println("Data: " + result.data);
            System.out.println("Success: " + result.success);
            System.out.println("Response Body: " + result.responseBody);
            System.out.println("Error: " + result.error);
        });
    }

    // Classe para armazenar o resultado de cada chamada
    static class CallResult {
        String data;
        boolean success;
        String responseBody;
        String error;
    }
}
