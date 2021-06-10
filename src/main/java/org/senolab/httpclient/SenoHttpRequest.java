package org.senolab.httpclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.StringTokenizer;

public class SenoHttpRequest {
    private HttpClient client;
    private HttpRequest.Builder requestBuilder;

    public SenoHttpRequest(String method, String url) {
        try {
            client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();
            requestBuilder = HttpRequest.newBuilder(new URI(url))
                    .timeout(Duration.ofMinutes(2));
            switch (method.toUpperCase()) {
                case "GET":
                    requestBuilder.GET();
                    break;
                case "POST":
                    requestBuilder.POST(BodyPublishers.noBody());
                    break;
                case "PUT":
                    requestBuilder.PUT(BodyPublishers.noBody());
                    break;
                case "DELETE":
                    requestBuilder.DELETE();
                    break;
                default:
                    requestBuilder.method(method.toUpperCase(), BodyPublishers.noBody());
                    break;
            }

        } catch (URISyntaxException uriE) {
            System.out.println("Something wrong with the URL that you specified. Error: ");
            System.out.println(stackTraceToString(uriE));
            System.exit(0);

        } catch (IllegalArgumentException il) {
            System.out.println("An Error occurred due to : ");
            System.out.println(stackTraceToString(il));
            System.exit(0);
        }
    }

    public SenoHttpRequest(String method, String url, String headerFile) {
        try {
            client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();
            requestBuilder = HttpRequest.newBuilder(new URI(url))
                    .timeout(Duration.ofMinutes(2));
            switch (method.toUpperCase()) {
                case "GET":
                    requestBuilder.GET();
                    break;
                case "POST":
                    requestBuilder.POST(BodyPublishers.noBody());
                    break;
                case "PUT":
                    requestBuilder.PUT(BodyPublishers.noBody());
                    break;
                case "DELETE":
                    requestBuilder.DELETE();
                    break;
                default:
                    requestBuilder.method(method.toUpperCase(), BodyPublishers.noBody());
                    break;
            }
            setHeaders(headerFile);

        } catch (URISyntaxException uriE) {
            System.out.println("Something wrong with the URL that you specified. Error: ");
            System.out.println(stackTraceToString(uriE));
            System.exit(0);
        } catch (IllegalArgumentException il) {
            System.out.println("An Error occurred due to : ");
            System.out.println(stackTraceToString(il));
            System.exit(0);
        } catch (IOException ioe) {
            System.out.println("Something wrong during input - output process: ");
            System.out.println(stackTraceToString(ioe));
            System.exit(0);
        }
    }

    public SenoHttpRequest(String method, String url, String headerFile, String bodyFileName) {
        try {
            client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();
            requestBuilder = HttpRequest.newBuilder(new URI(url))
                    .timeout(Duration.ofMinutes(2));
            switch (method.toUpperCase()) {
                case "POST":
                    requestBuilder.POST(BodyPublishers.ofFile(Path.of(bodyFileName)));
                    break;
                case "PUT":
                    requestBuilder.PUT(BodyPublishers.ofFile(Path.of(bodyFileName)));
                    break;
                default:
                    requestBuilder.method(method.toUpperCase(), BodyPublishers.ofFile(Path.of(bodyFileName)));
                    break;
            }
            setHeaders(headerFile);

        } catch (URISyntaxException uriE) {
            System.out.println("Something wrong with the URL that you specified. Error: ");
            System.out.println(stackTraceToString(uriE));
            System.exit(0);
        } catch (IllegalArgumentException il) {
            System.out.println("An Error occurred due to : ");
            System.out.println(stackTraceToString(il));
            System.exit(0);
        } catch (IOException ioe) {
            System.out.println("Something wrong during input - output process: ");
            System.out.println(stackTraceToString(ioe));
            System.exit(0);
        }
    }

    public void execute() {
        try {
            HttpRequest request = requestBuilder.build();
            long start = System.currentTimeMillis();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            long end = System.currentTimeMillis();
            HttpHeaders requestHeaders = response.request().headers();
            HttpHeaders responseHeaders = response.headers();
            System.out.println("HTTP Request headers: ");
            for (String key : requestHeaders.map().keySet()) {
                System.out.println(key + ": " + requestHeaders.map().get(key));
            }
            System.out.println();
            System.out.println("HTTP version: " + response.version());
            response.sslSession().ifPresent(sslSession -> System.out.println("\nSSL protocol: " + sslSession.getProtocol()));
            System.out.println("\nHTTP Response code: " + response.statusCode());
            System.out.println("\nHTTP Response headers: ");
            for (String key : responseHeaders.map().keySet()) {
                System.out.println(key + ": " + responseHeaders.map().get(key));
            }
            System.out.println("\nHTTP Response body: \n"+response.body());
            System.out.println("Time taken: "+(end-start)+" ms");
        }
        catch (IOException ioe) {
            System.out.println("Something wrong during input - output process: ");
            System.out.println(stackTraceToString(ioe));
            System.exit(0);
        } catch (InterruptedException ie) {
            System.out.println("The HTTP communication is interrupted:  ");
            System.out.println(stackTraceToString(ie));
            System.exit(0);
        }
    }

    private String stackTraceToString(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    private void setHeaders(String headerFile) throws IOException {
        if (headerFile.equalsIgnoreCase("json")) {
            requestBuilder.header("Content-Type", "application/json");
        } else if(headerFile.equalsIgnoreCase("xml")) {
            requestBuilder.header("Content-Type", "application/xml");
        } else if(Files.exists(Path.of(headerFile))) {
            var headers = Files.readString(Path.of(headerFile)).split(",");
            if (headers.length >= 2  && (headers.length % 2 == 0)) {
                requestBuilder.headers(headers);
            }
        }

    }
}
