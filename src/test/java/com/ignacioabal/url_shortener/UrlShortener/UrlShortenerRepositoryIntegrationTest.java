package com.ignacioabal.url_shortener.UrlShortener;

import com.ignacioabal.url_shortener.urlShortener.UrlAlias;
import com.ignacioabal.url_shortener.urlShortener.UrlShortenerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UrlShortenerRepositoryIntegrationTest {

    @Autowired
    UrlShortenerRepository urlShortenerRepository;

    @Test
    void shouldSReturnTrueIfObjecctExists() {
        UrlAlias mockUrlAlias = new UrlAlias("www.google.com", "abcd1234");

        urlShortenerRepository.save(mockUrlAlias);

        Optional<UrlAlias> optionalUrlAlias = urlShortenerRepository.findUrlByAlias(mockUrlAlias.getAlias());

        assertTrue(optionalUrlAlias.isPresent());
        assertEquals(optionalUrlAlias.get(), mockUrlAlias);
        assertTrue(urlShortenerRepository.existsByAlias(mockUrlAlias.getAlias()));

    }

    @Test
    void shouldReturnFalseIfObjectDoesNotExist() {
        String mockAlias = "nonExistentAlias";

        assertFalse(urlShortenerRepository.existsByAlias(mockAlias));
    }

    @AfterEach
    void tearDown() {
        urlShortenerRepository.deleteAll();
    }

}