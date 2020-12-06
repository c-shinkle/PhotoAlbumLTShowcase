package com.shinkle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhotoServiceUnitTest {

    @Mock
    private WebClient webClientMock;
    @Mock
    private RequestHeadersSpec requestHeadersMock;
    @Mock
    private RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;

    PhotoService photoService;

    @BeforeEach
    void setUp() {
        photoService = new PhotoService(webClientMock);
    }

    @Test
    void shouldCallOnlineWebService() {
        Photo[] expectedPhotos = {Photo.builder().build()};
        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri(any(Function.class))).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(Photo[].class)).thenReturn(Mono.just(expectedPhotos));

        photoService.retrievePhotosIdsAndTitles("3");

        verify(webClientMock).get();
        verify(requestHeadersUriMock).uri(any(Function.class));
        verify(requestHeadersMock).retrieve();
        verify(responseMock).bodyToMono(Photo[].class);
    }

    @Test
    void shouldBuildListOfIdsAndTitles() {
        int expectedAlbumId = 3;

        int expectedPhotoIdA = 1;
        String expectedPhotoTitleA = "Lorem ipsum dolor sit amet";
        Photo expectedPhotoA = Photo.builder()
                .id(expectedPhotoIdA)
                .albumId(expectedAlbumId)
                .title(expectedPhotoTitleA)
                .build();

        int expectedPhotoIdB = 2;
        String expectedPhotoTitleB = "consectetur adipiscing elit";
        Photo expectedPhotoB = Photo.builder()
                .id(expectedPhotoIdB)
                .albumId(expectedAlbumId)
                .title(expectedPhotoTitleB)
                .build();

        Photo[] expectedPhotos = {expectedPhotoA, expectedPhotoB};
        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri(any(Function.class))).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(Photo[].class)).thenReturn(Mono.just(expectedPhotos));

        List<String> actualIdsAndTitles = photoService.retrievePhotosIdsAndTitles(String.valueOf(expectedAlbumId));

        verify(webClientMock).get();
        verify(requestHeadersUriMock).uri(any(Function.class));
        verify(requestHeadersMock).retrieve();
        verify(responseMock).bodyToMono(Photo[].class);
        assertThat(actualIdsAndTitles).containsExactly(
                format("[%d] %s", expectedPhotoIdA, expectedPhotoTitleA),
                format("[%d] %s", expectedPhotoIdB, expectedPhotoTitleB));
    }
}