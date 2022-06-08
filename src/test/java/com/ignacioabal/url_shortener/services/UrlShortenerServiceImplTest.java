package com.ignacioabal.url_shortener.services;

import com.ignacioabal.url_shortener.models.UrlAlias;
import com.ignacioabal.url_shortener.repositories.UrlShortenerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@SpringBootTest
class UrlShortenerServiceImplTest extends UrlShortenerServiceImpl {

    @Autowired
    UrlShortenerService urlShortenerService;

    @MockBean
    UrlShortenerRepository urlShortenerRepository;


    @BeforeEach
    void setUp() {
        String mockUrl = "www.google.com";
        String mockAlias = "abcd1234";

        UrlAlias urlAlias = new UrlAlias(mockUrl, mockAlias);


    }



    @Nested
    class createUrlTests{

        @Test
        void shouldCreateUrlIfItDoesNotExist() {
            String mockUrl = "www.google.com";
            String mockAlias = "abcd1234";

            UrlAlias urlAlias = new UrlAlias(mockUrl, mockAlias);
            Mockito.when(urlShortenerRepository.save(new UrlAlias(mockUrl))).thenReturn(urlAlias);


            UrlAlias mockUrlAlias = new UrlAlias(mockUrl);

            UrlAlias returnedUrlAlias = urlShortenerService.createUrl(mockUrlAlias).getBody();


            assert returnedUrlAlias != null;
            Assertions.assertEquals(mockUrlAlias.getUrl(), returnedUrlAlias.getUrl());
            Assertions.assertNotNull(returnedUrlAlias.getAlias());
        }

        @Test
        void shouldThrowExceptionIfResourceAlreadyExists() {
            UrlShortenerService spyUrlShortenerService = Mockito.spy(urlShortenerService);


            String mockUrl = "www.google.com";
            String mockAlias = "abcd1234";
            Optional<UrlAlias> emptyUrlAliasOptional = Optional.empty();

            UrlAlias alreadyExistingMockUrlAlias = new UrlAlias(mockUrl, mockAlias);
            UrlAlias inputMockUrlAlias = new UrlAlias(mockUrl);


            Mockito.when(urlShortenerRepository.existsByAlias(mockAlias)).thenReturn(true).thenReturn(false);


            //TODO


        }


    }

    @Nested
    class deleteUrlTests{

    }

    @Nested
    class modifyUrlTests{

    }

    @Nested
    class getUrlTests{

        @Test
        void shouldReturnUrlIfAliasIsFound() {
            String mockAlias = "abcd1234";
            String mockUrl = "www.google.com";
            UrlAlias mockUrlAlias = new UrlAlias(mockUrl, mockAlias);

            Mockito.when(urlShortenerRepository.findUrlByAlias(mockAlias)).thenReturn(Optional.of(mockUrlAlias));
            RedirectView redirectView = urlShortenerService.getUrl(mockAlias);

            Assertions.assertEquals(redirectView.getUrl(), "www.google.com");
        }



    }
}