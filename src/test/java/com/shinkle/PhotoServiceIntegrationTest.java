package com.shinkle;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

class PhotoServiceIntegrationTest {

    PhotoService photoService;
    WebClient webClient;
    MockWebServer mockWebServer;
    ReactorClientHttpConnector connector;

    @BeforeEach
    void setUp() {
        connector = new ReactorClientHttpConnector();
        mockWebServer = new MockWebServer();
        webClient = WebClient
                .builder()
                .clientConnector(connector)
                .baseUrl(this.mockWebServer.url("/").toString())
                .build();
        photoService = new PhotoService(webClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void shouldReturnListOfPhotoIdsAndTitles() {
        int expectedAlbumId = 3;
        int expectedPhotoId = 101;
        String expectedPhotoTitle = "incidunt alias vel enim";
        //language=JSON
        String givenBodyTemplate = "[" +
                "{" +
                "   \"albumId\": %d,\n" +
                "    \"id\": %d,\n" +
                "    \"title\": \"%s\",\n" +
                "    \"url\": \"https://via.placeholder.com/600/e743b\",\n" +
                "    \"thumbnailUrl\": \"https://via.placeholder.com/150/e743b\"" +
                "}" +
                "]";
        MockResponse response = new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(format(givenBodyTemplate, expectedAlbumId, expectedPhotoId, expectedPhotoTitle));
        mockWebServer.enqueue(response);

        List<String> actualPhotoIdsAndTitles = photoService.retrievePhotosIdsAndTitles(valueOf(expectedAlbumId));

        assertThat(actualPhotoIdsAndTitles)
                .containsAnyOf(String.format("[%d] %s", expectedPhotoId, expectedPhotoTitle));
    }
}