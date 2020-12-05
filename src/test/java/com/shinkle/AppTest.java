package com.shinkle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AppTest {

    //    OutputStream consoleText;
//    PrintStream console;
    App app;

    private int errorCode;

    @BeforeEach
    void setup() {
//        consoleText = new ByteArrayOutputStream();
//        console = System.out;
//        System.setOut(new PrintStream(consoleText));
        errorCode = 0;
        app = new App() {
            @Override
            public void exit(int errorCode) {
                AppTest.this.errorCode = errorCode;
            }
        };
    }

    @Test
    void shouldCallPhotoServiceWithArgs() {
        String expectedAlbumId = "1";
        PhotoService mockPhotoService = mock(PhotoService.class);
        when(mockPhotoService.retrievePhotos(expectedAlbumId)).thenReturn(Collections.emptyList());

        app.execute(mockPhotoService, expectedAlbumId);

        verify(mockPhotoService, only()).retrievePhotos(expectedAlbumId);
    }

    @Test
    void shouldExitWithErrorCodeOneWithNoArgs() {
        PhotoService mockPhotoService = mock(PhotoService.class);

        app.execute(mockPhotoService);

        verifyNoInteractions(mockPhotoService);
        assertThat(errorCode).isEqualTo(1);
    }

    @Test
    void shouldExitWithErrorCodeOneWithNonIntArg() {
        PhotoService mockPhotoService = mock(PhotoService.class);

        app.execute(mockPhotoService, "one");

        verifyNoInteractions(mockPhotoService);
        assertThat(errorCode).isEqualTo(1);
    }


}
