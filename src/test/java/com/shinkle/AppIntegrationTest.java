package com.shinkle;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class AppIntegrationTest {

    OutputStream consoleText;
    PrintStream console;
    int actualErrorCode;
    static MockWebServer mockWebServer;
    PhotoService photoService;
    App app;

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
        consoleText = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(consoleText));
        actualErrorCode = 0;
        app = new App() {
            @Override
            public void exit(int errorCode) {
                AppIntegrationTest.this.actualErrorCode = errorCode;
            }
        };
        photoService = new PhotoService(WebClient.create("http://localhost:" + mockWebServer.getPort()));
    }

    @Test
    void shouldDisplayListOfPhotoIdsAndTitles() {
        int expectedAlbumId = 3;
        int expectedPhotoIdA = 101;
        String expectedPhotoTitleA = "incidunt alias vel enim";
        //language=JSON
        String givenBody = "[" +
                "   {" +
                "       \"albumId\": " + expectedAlbumId + ",\n" +
                "       \"id\": " + expectedPhotoIdA + ",\n" +
                "       \"title\": \"" + expectedPhotoTitleA + "\",\n" +
                "       \"url\": \"https://via.placeholder.com/600/e743b\",\n" +
                "       \"thumbnailUrl\": \"https://via.placeholder.com/150/e743b\"" +
                "   }" +
                "]";
        MockResponse response = new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(givenBody);
        mockWebServer.enqueue(response);

        app.execute(photoService, "3");

        String expectedConsoleOutput = "[101] incidunt alias vel enim";
        assertThat(consoleText.toString()).contains(expectedConsoleOutput);
        assertThat(actualErrorCode).isEqualTo(0);
    }

    @Test
    void shouldDisplayUsageAndErrorMessageWhenNoArgs() {
        app.execute(photoService);

        assertThat(consoleText.toString()).contains(App.MSG_TOO_FEW_ARGUMENTS);
        assertThat(consoleText.toString()).contains(App.USAGE);
        assertThat(actualErrorCode).isEqualTo(App.ERROR_CODE);
    }

    @Test
    void shouldDisplayUsageAndErrorMessageWhenArgNotInteger() {
        app.execute(photoService, "foo");

        assertThat(consoleText.toString()).contains(App.MSG_ALBUM_ID_MUST_BE_INTEGER);
        assertThat(consoleText.toString()).contains(App.USAGE);
        assertThat(actualErrorCode).isEqualTo(App.ERROR_CODE);
    }
}
