package com.ignacioabal.url_shortener.services;

import com.ignacioabal.url_shortener.models.UrlAlias;
import com.ignacioabal.url_shortener.repositories.UrlShortenerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
class UrlShortenerServiceImplTest {

    @Autowired
    UrlShortenerService urlShortenerService;

    @MockBean
    UrlShortenerRepository urlShortenerRepository;


    @BeforeEach
     void setUp() {
        String mockUrl = "www.google.com";
        String mockAlias = "abcd1234";

        UrlAlias urlAlias = new UrlAlias(mockUrl,mockAlias);

        Mockito.when(urlShortenerRepository.findUrlByAlias(mockAlias)).thenReturn(Optional.of(urlAlias));

//        Mockito.when(urlShortenerRepository.findUrlByAlias())


        Mockito.when(urlShortenerRepository.save(new UrlAlias(mockUrl))).thenReturn(urlAlias);

    }



    @Nested
    class createUrlTests{

        @Test
        void shouldCreateUrlIfItDoesNotExist(){
            String mockUrl = "www.google.com";
            String mockAlias = "abcd1234";

            UrlAlias mockUrlAlias = new UrlAlias(mockUrl);


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
        void shouldReturnUrlIfAliasIsFound(){
            String mockAlias = "abcd1234";

            String foundUrl = urlShortenerService.getUrl(mockAlias);

            Assertions.assertEquals(foundUrl,"www.google.com");
        }



    }
}