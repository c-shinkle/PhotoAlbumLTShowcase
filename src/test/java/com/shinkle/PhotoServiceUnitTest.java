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
        PhotoList expectedPhotoList = PhotoList.builder().build();
        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri(any(Function.class))).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(PhotoList.class)).thenReturn(Mono.just(expectedPhotoList));

        photoService.retrievePhotosIdsAndTitles("3");

        verify(webClientMock).get();
        verify(requestHeadersUriMock).uri(any(Function.class));
        verify(requestHeadersMock).retrieve();
        verify(responseMock).bodyToMono(PhotoList.class);
    }

    @Test
    void shouldBuildListOfIdsAndTitles() {
        int expectedPhotoId = 1;
        int expectedAlbumId = 3;
        String expectedPhotoTitle = "Lorem ipsum";
        PhotoList expectedPhotoList = PhotoList.builder()
                .photo(Photo.builder()
                        .id(expectedPhotoId)
                        .albumId(expectedAlbumId)
                        .title(expectedPhotoTitle)
                        .build())
                .build();
        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri(any(Function.class))).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(PhotoList.class)).thenReturn(Mono.just(expectedPhotoList));

        List<String> actualIdsAndTitles = photoService.retrievePhotosIdsAndTitles(String.valueOf(expectedAlbumId));

        verify(webClientMock).get();
        verify(requestHeadersUriMock).uri(any(Function.class));
        verify(requestHeadersMock).retrieve();
        verify(responseMock).bodyToMono(PhotoList.class);
        assertThat(actualIdsAndTitles).containsExactly(String.format("[%d] %s", expectedPhotoId, expectedPhotoTitle));
    }
}