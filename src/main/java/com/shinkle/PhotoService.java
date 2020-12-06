package com.shinkle;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class PhotoService {

    private final WebClient webClient;

    public PhotoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<String> retrievePhotosIdsAndTitles(String albumId) {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/photos").queryParam("albumId", albumId).build())
                .retrieve()
                .bodyToMono(Photo.class);

        return null;
    }
}
