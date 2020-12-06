package com.shinkle;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

class PhotoServiceIntegrationTest {

    static MockWebServer mockWebServer;
    PhotoService photoService;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void initialize() {
        photoService = new PhotoService(WebClient.create("http://localhost:" + mockWebServer.getPort()));
    }

    @Test
    void shouldReturnListOfPhotoIdsAndTitles() {
        int expectedAlbumId = 3;
        int expectedPhotoIdA = 101;
        int expectedPhotoIdB = 102;
        String expectedPhotoTitleA = "incidunt alias vel enim";
        String expectedPhotoTitleB = "eaque iste corporis tempora vero distinctio consequuntur nisi nesciunt";
        //language=JSON
        String givenBody = "[" +
                "   {" +
                "       \"albumId\": " + expectedAlbumId + ",\n" +
                "       \"id\": " + expectedPhotoIdA + ",\n" +
                "       \"title\": \"" + expectedPhotoTitleA + "\",\n" +
                "       \"url\": \"https://via.placeholder.com/600/e743b\",\n" +
                "       \"thumbnailUrl\": \"https://via.placeholder.com/150/e743b\"" +
                "   }," +
                "   {" +
                "       \"albumId\": " + expectedAlbumId + ",\n" +
                "       \"id\": " + expectedPhotoIdB + ",\n" +
                "       \"title\": \"" + expectedPhotoTitleB + "\",\n" +
                "       \"url\": \"https://via.placeholder.com/600/e743b\",\n" +
                "       \"thumbnailUrl\": \"https://via.placeholder.com/150/e743b\"" +
                "   }" +
                "]";
        MockResponse response = new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(givenBody);
        mockWebServer.enqueue(response);

        Photo[] actualPhotos = photoService.retrievePhotosIdsAndTitles(valueOf(expectedAlbumId));

        assertThat(actualPhotos).containsExactlyInAnyOrder(
                Photo.builder().id(expectedPhotoIdA).title(expectedPhotoTitleA).albumId(expectedAlbumId).build(),
                Photo.builder().id(expectedPhotoIdB).title(expectedPhotoTitleB).albumId(expectedAlbumId).build()
        );
    }
}