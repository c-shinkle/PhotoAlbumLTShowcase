package com.shinkle;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class PhotoService {

    private final WebClient webClient;

    public PhotoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Photo> retrievePhotosIdsAndTitles(String albumId) {
        Mono<List<Photo>> photoArrayMono = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/photos").queryParam("albumId", albumId).build())
                .retrieve()
                .bodyToMono(Photo[].class)
                .flatMapIterable(Arrays::asList)
                .collectList();

        return photoArrayMono.block();
    }
}
