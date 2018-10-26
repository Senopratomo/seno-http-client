package org.senolab.senohttpclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args)  {
        if (args.length > 1 && args[1] != null) {
            httpcall(args[0],args[1]);
        } else if (args.length == 1 && args[0] != null) {
            httpcall(args[0],"");
        } else {
            System.out.println("Please specify a valid URL");
        }

    }

    private static void httpcall(String uri, String filePath) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(uri))
                    .timeout(Duration.ofMinutes(2))
                    .GET();
            if (filePath.length() > 2) {
                var headers = new StringTokenizer(Files.readString(Path.of(filePath)),",");
                if (headers.countTokens() > 1 && (headers.countTokens() % 2 == 0)) {
                    while(headers.hasMoreTokens() )
                        requestBuilder.header(headers.nextToken(), headers.nextToken());
                }

            }
            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpHeaders requestHeaders = response.request().headers();
            HttpHeaders responseHeaders = response.headers();
            System.out.println("HTTP Request headers: ");
            for(String key : requestHeaders.map().keySet()) {
                System.out.println(key+": "+requestHeaders.map().get(key));
            }
            System.out.println();
            System.out.println("HTTP version: "+response.version());
            response.sslSession().ifPresent(sslSession -> System.out.println("SSL protocol: "+sslSession.getProtocol()));
            System.out.println("HTTP Response code: "+response.statusCode());
            System.out.println("HTTP Response headers: ");
            for(String key : responseHeaders.map().keySet()) {
                System.out.println(key+": "+responseHeaders.map().get(key));
            }
        } catch (IllegalArgumentException il) {
            System.out.println("An Error occurred due to : "+il.getMessage());
        } catch (IOException ioe) {
            System.out.println("Something wrong during input - output process");
        } catch (InterruptedException ie) {
            System.out.println("The HTTP communication is interrupted ");
        }
    }
}
