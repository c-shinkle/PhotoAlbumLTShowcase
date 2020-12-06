package com.shinkle;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PhotoService {

    private final WebClient webClient;

    public PhotoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<String> retrievePhotosIdsAndTitles(String albumId) {
        Mono<PhotoList> photoListMono = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/photos").queryParam("albumId", albumId).build())
                .retrieve()
                .bodyToMono(PhotoList.class);

        PhotoList photoList = photoListMono.block();

        return Objects.requireNonNull(photoList).getPhotos().stream()
                .map((photo -> String.format("[%d] %s", photo.getId(), photo.getTitle())))
                .collect(Collectors.toList());
    }
}
