package com.ignacioabal.url_shortener.UrlShortener;

import com.ignacioabal.url_shortener.urlShortener.UrlAlias;
import com.ignacioabal.url_shortener.urlShortener.UrlShortenerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
public class UrlShortenerIntegrationTest {



    @Autowired
    UrlShortenerRepository urlShortenerRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCreateAnAliasWithValidUrl() throws Exception {
        String mockUrl = "www.google.com";
        String mockAlias = "abcd1234";
        UrlAlias mockCreatedUrlAlias = new UrlAlias(mockUrl, mockAlias);

        String mockRequestContent = "{\"url\":\"www.google.com\"}";

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockRequestContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url").value("www.google.com"))
                .andExpect(jsonPath("$.alias").isNotEmpty());

    }


    @AfterEach
    void afterAll(){
        urlShortenerRepository.deleteAll();
    }
}
