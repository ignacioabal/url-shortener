package com.ignacioabal.url_shortener.UrlShortener;

import com.ignacioabal.url_shortener.urlShortener.UrlAlias;
import com.ignacioabal.url_shortener.urlShortener.UrlShortenerRepository;
import com.ignacioabal.url_shortener.urlShortener.UrlShortenerService;
import com.ignacioabal.url_shortener.urlShortener.UrlShortenerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class UrlShortenerServiceImplTest extends UrlShortenerServiceImpl {

    @Autowired
    UrlShortenerService urlShortenerService;

    @MockBean
    UrlShortenerRepository urlShortenerRepository;

    @Nested
    class createUrlTests {

        @Test
        void shouldReturnValidObjectIfCreated() {
            String mockUrl = "www.google.com";
            String mockAlias = "abcd1234";

            UrlAlias urlAlias = new UrlAlias(mockUrl, mockAlias);
            Mockito.when(urlShortenerRepository.save(new UrlAlias(mockUrl))).thenReturn(urlAlias);


            UrlAlias mockUrlAlias = new UrlAlias(mockUrl);

            UrlAlias returnedUrlAlias = urlShortenerService.createUrl(mockUrlAlias).getBody();


            assert returnedUrlAlias != null;
            assertEquals(mockUrlAlias.getUrl(), returnedUrlAlias.getUrl());
            Assertions.assertNotNull(returnedUrlAlias.getAlias());
        }

        @Test
        void shouldRegenerateAliasIfAliasAlreadyExists() {
            String mockUrl = "www.google.com";
            UrlAlias mockUrlAlias = new UrlAlias(mockUrl);

            when(urlShortenerRepository.existsByAlias(Mockito.anyString())).thenReturn(true);
            when(urlShortenerRepository.existsByAlias(Mockito.anyString())).thenReturn(true);
            when(urlShortenerRepository.existsByAlias(Mockito.anyString())).thenReturn(false);

            assertEquals(HttpStatus.CREATED, urlShortenerService.createUrl(mockUrlAlias).getStatusCode());

            verify(urlShortenerRepository).save(Mockito.any(UrlAlias.class));
        }

    }

    @Nested
    class deleteUrlTests {

        @Test
        void shouldCallDeleteByUrlRepositoryMethod() {
            String mockAlias = "abcd1234";
            when(urlShortenerRepository.existsByAlias(mockAlias)).thenReturn(true);

            urlShortenerService.deleteUrl(mockAlias);

            verify(urlShortenerRepository, times(1)).deleteUrlByAlias(mockAlias);
        }


        @Test
        void shouldReturnOkStatusIfFoundAndDeleted() {
            String mockAlias = "abcd1234";

            doNothing().when(urlShortenerRepository).deleteUrlByAlias(mockAlias);
            when(urlShortenerRepository.existsByAlias(mockAlias)).thenReturn(true);

            ResponseEntity<UrlAlias> responseEntity = urlShortenerService.deleteUrl(mockAlias);

            assertEquals(responseEntity, new ResponseEntity<UrlAlias>(HttpStatus.OK));
        }

        @Test
        void shouldThrowNotFoundStatusIfAliasIsNotFound() {
            String mockAlias = "abcd1234";

            when(urlShortenerRepository.existsByAlias(mockAlias)).thenReturn(false);

            assertThrows(ResponseStatusException.class, () -> urlShortenerService.deleteUrl(mockAlias));

        }
    }

    @Nested
    class modifyUrlTests {
        @Test
        void shouldCallRepositorySaveAndReturnOkStatusIfObjectExists() {
            String mockAlias = "abcd1234";
            String mockUrl = "www.google.com";
            String mockModifiedUrl = "www.yahoo.com";

            UrlAlias mockUrlAlias = new UrlAlias(mockUrl, mockAlias);
            UrlAlias mockModifiedUrlAlias = new UrlAlias(mockModifiedUrl, mockAlias);


            when(urlShortenerRepository.findUrlByAlias(mockAlias)).thenReturn(Optional.of(mockUrlAlias));

            ResponseEntity<UrlAlias> returnedResponseEntity = urlShortenerService.modifyUrl(mockAlias, new UrlAlias(mockModifiedUrl));

            verify(urlShortenerRepository).save(eq(mockModifiedUrlAlias));

            assertEquals(returnedResponseEntity, new ResponseEntity<>(mockModifiedUrlAlias, HttpStatus.OK));
        }

        @Test
        void shouldThrowNotFoundIfAliasDoesNotExist() {
            String mockAlias = "abcd1234";
            String mockModifiedUrl = "www.yahoo.com";

            when(urlShortenerRepository.findUrlByAlias(mockAlias)).thenReturn(Optional.empty());

            assertThrows(ResponseStatusException.class, () -> urlShortenerService.modifyUrl(mockAlias, new UrlAlias(mockModifiedUrl)));
        }


    }

    @Nested
    class getUrlTests {

        @Test
        void shouldReturnUrlIfAliasIsFound() {
            String mockAlias = "abcd1234";
            String mockUrl = "www.google.com";
            UrlAlias mockUrlAlias = new UrlAlias(mockUrl, mockAlias);

            Mockito.when(urlShortenerRepository.findUrlByAlias(mockAlias)).thenReturn(Optional.of(mockUrlAlias));
            UrlAlias urlAlias = urlShortenerService.getUrl(mockAlias).getBody();

            assert urlAlias != null; //Shouldn't be null by logic, but adding this just in case.
            assertEquals(urlAlias.getUrl(), "www.google.com");
        }

        @Test
        void shouldThrowExceptionIfObjectIsNotFound() {
            String mockAlias = "abcd1234";

            Mockito.when(urlShortenerRepository.findUrlByAlias(mockAlias)).thenReturn(Optional.empty());


            assertThrows(ResponseStatusException.class, () -> urlShortenerService.getUrl(mockAlias));
        }


    }
}