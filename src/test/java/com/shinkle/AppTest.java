package com.shinkle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class AppTest {

    //    OutputStream consoleText;
//    PrintStream console;
    App app;

    @BeforeEach
    void setup() {
//        consoleText = new ByteArrayOutputStream();
//        console = System.out;
//        System.setOut(new PrintStream(consoleText));
        app = new App();
    }

    @Test
    void shouldCallPhotoServiceWithArgs() {
        String expectedAlbumId = "someAlbumId";
        PhotoService photoService = mock(PhotoService.class);
        when(photoService.retrievePhotos(expectedAlbumId)).thenReturn(Collections.emptyList());

        app.execute(photoService, expectedAlbumId);

        verify(photoService, only()).retrievePhotos(expectedAlbumId);
    }
}
