package com.shinkle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AppUnitTest {

    App app;
    int actualErrorCode;

    @BeforeEach
    void setup() {
        actualErrorCode = 0;
        app = new App() {
            @Override
            public void exit(int errorCode) {
                AppUnitTest.this.actualErrorCode = errorCode;
            }
        };
    }

    @Test
    void shouldCallPhotoServiceWithArgs() {
        String expectedAlbumId = "1";
        PhotoService mockPhotoService = mock(PhotoService.class);
        when(mockPhotoService.retrievePhotosIdsAndTitles(expectedAlbumId)).thenReturn(Collections.emptyList());

        app.execute(mockPhotoService, expectedAlbumId);

        verify(mockPhotoService, atMostOnce()).retrievePhotosIdsAndTitles(expectedAlbumId);
    }

    @Test
    void shouldExitWithErrorCodeOneWithNoArgs() {
        PhotoService mockPhotoService = mock(PhotoService.class);

        app.execute(mockPhotoService);

        verifyNoInteractions(mockPhotoService);
        assertThat(actualErrorCode).isEqualTo(1);
    }

    @Test
    void shouldExitWithErrorCodeOneWithNonIntArg() {
        PhotoService mockPhotoService = mock(PhotoService.class);

        app.execute(mockPhotoService, "one");

        verifyNoInteractions(mockPhotoService);
        assertThat(actualErrorCode).isEqualTo(1);
    }
}
