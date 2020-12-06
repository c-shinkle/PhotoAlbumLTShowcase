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

import java.util.function.Function;

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
        Photo expectedPhoto = Photo.builder().build();
        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri(any(Function.class))).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(Photo.class)).thenReturn(Mono.just(expectedPhoto));

        photoService.retrievePhotosIdsAndTitles("3");

        verify(webClientMock).get();
        verify(requestHeadersUriMock).uri(any(Function.class));
        verify(requestHeadersMock).retrieve();
        verify(responseMock).bodyToMono(Photo.class);
    }
}