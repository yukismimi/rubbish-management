package com.yukismimi.rubbishmanagement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
@PropertySource({"api.properties"})
public class Api {

    @Value("${key}")
    private String key;

    @Value("${uri}")
    private String baseUri;

    @Value("${proxy-uri}")
    private String proxyUri;

    @Value("${proxy-port}")
    private int proxyPort;

    @Value("${proxy-username}")
    private String proxyUserName;

    @Value("${proxy-password}")
    private String proxyPassword;

    public ApiResult call(String word) throws IOException, InterruptedException {
        return call(word, 1, 1);
    }

    public ApiResult call(String word, int num) throws IOException, InterruptedException {
        return call(word, num, 1);
    }

    public ApiResult call(String word, int num, int page) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
//                .proxy(ProxySelector.of(new InetSocketAddress(proxyUri, proxyPort)))
//                .authenticator(new Authenticator() {
//                    @Override
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(proxyUserName, proxyPassword.toCharArray());
//                    }
//                })
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUri.concat(String.format("?key=%s&word=%s&num=%d&page=%d", key, word, num, page))))
                .GET()
                .build();

        HttpResponse response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body().toString(), ApiResult.class);
    }
}
