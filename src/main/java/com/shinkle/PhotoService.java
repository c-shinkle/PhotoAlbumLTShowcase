package com.shinkle;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PhotoService {

    private final WebClient webClient;

    public PhotoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<String> retrievePhotosIdsAndTitles(String albumId) {
        Mono<Photo[]> photoArrayMono = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/photos").queryParam("albumId", albumId).build())
                .retrieve()
                .bodyToMono(Photo[].class);

        Photo[] photos = photoArrayMono.block();

        return Arrays.stream(Objects.requireNonNull(photos))
                .map((photo -> String.format("[%d] %s", photo.getId(), photo.getTitle())))
                .collect(Collectors.toList());
    }
}
