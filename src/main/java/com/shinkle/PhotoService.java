package com.shinkle;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class PhotoService {

    WebClient webClient;

    public PhotoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<String> retrievePhotosIdsAndTitles(String albumId) {

        return null;
    }
}
