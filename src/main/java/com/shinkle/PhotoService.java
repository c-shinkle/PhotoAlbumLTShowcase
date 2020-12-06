package com.shinkle;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class PhotoService {

    private final WebClient webClient;

    public PhotoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Photo[] retrievePhotosIdsAndTitles(String albumId) {
        Mono<Photo[]> photoArrayMono = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/photos").queryParam("albumId", albumId).build())
                .retrieve()
                .bodyToMono(Photo[].class);

        Photo[] photos = photoArrayMono.block();
        return Objects.requireNonNull(photos);
    }
}
