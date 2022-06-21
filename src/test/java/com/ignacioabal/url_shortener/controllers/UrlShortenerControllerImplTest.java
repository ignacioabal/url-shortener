package com.ignacioabal.url_shortener.controllers;

import com.ignacioabal.url_shortener.urlShortener.UrlAlias;
import com.ignacioabal.url_shortener.urlShortener.UrlShortenerController;
import com.ignacioabal.url_shortener.urlShortener.UrlShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlShortenerController.class)
class UrlShortenerControllerImplTest {

    @Autowired
    private UrlShortenerController urlShortenerController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService urlShortenerService;

    @BeforeEach
    public void setUp() {

    }

    @Nested
    @DisplayName("Tests for createUrl Method")
    class createUrlTests {
        @Test
        void shouldCreateAnAliasWithValidUrl() throws Exception {
            String mockUrl = "www.google.com";
            String mockAlias = "abcd1234";

            String mockRequestContent = "{\"url\":\"www.google.com\"}";
            UrlAlias mockCreatedUrlAlias = new UrlAlias(mockUrl, mockAlias);


            Mockito.when(urlShortenerService.createUrl(Mockito.any(UrlAlias.class))).thenReturn(new ResponseEntity<>(mockCreatedUrlAlias, HttpStatus.CREATED));

            mockMvc.perform(post("/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mockRequestContent))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.url").value("www.google.com"))
                    .andExpect(jsonPath("$.alias").value("abcd1234"));

        }

        @Test
        void shouldReturnBadRequestStatusWithEmptyRequestBody() throws Exception {
            mockMvc.perform(post("/")
                            .content("")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestStatusIfBodyIsNotValid() throws Exception {


            mockMvc.perform(post("/")
                            .content("12348")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnAlreadyExistsStatusCodeIfResourceAlreadyExists() throws Exception {
            String mockRequestContent = "{\"url\":\"www.google.com\"}";

            Mockito.when(urlShortenerService.createUrl(Mockito.any(UrlAlias.class))).thenReturn(new ResponseEntity<>(HttpStatus.CONFLICT));


            mockMvc.perform(post("/")
                            .content(mockRequestContent)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isConflict());
        }


    }


    @Nested
    @DisplayName("Tests for deleteUrl Method")
    class deleteUrl {
        @Test
        void shouldReturnOkStatusIfResourceIsDeleted() throws Exception {

            Mockito.when(urlShortenerService.deleteUrl(Mockito.anyString())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

            mockMvc.perform(delete("/1234abcd"))
                    .andExpect(status().isOk());
        }

        @Test
        void shouldReturn404IfResourceIsNotFound() throws Exception {

            Mockito.when(urlShortenerService.deleteUrl(Mockito.anyString())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

            mockMvc.perform(delete("/1234abcd")).andExpect(status().isNotFound());
        }

    }

    @Nested
    @DisplayName("Tests for modifyUrl Method")
    class modifyUrl {


        @Test
        void shouldReturnObjectIfModified() throws Exception {

            String mockUrl = "www.bing.com";
            String mockAlias = "abcd1234";

            String mockRequestContent = "{\"url\":\"www.bing.com\"}";
            UrlAlias mockUpdatedUrlAlias = new UrlAlias(mockUrl, mockAlias);


            Mockito.when(urlShortenerService.modifyUrl(Mockito.anyString(), Mockito.any(UrlAlias.class))).thenReturn(new ResponseEntity<>(mockUpdatedUrlAlias, HttpStatus.OK));


            mockMvc.perform(put("/abcd1234")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mockRequestContent))
                    .andExpect(jsonPath("$.url").value("www.bing.com"))
                    .andExpect(jsonPath("$.alias").value("abcd1234"))
                    .andExpect(status().isOk());


        }

        @Test
        void shouldReturnNotFoundStatusIfNotFound() throws Exception {
            String mockRequestContent = "{\"url\":\"www.bing.com\"}";

            Mockito.when(urlShortenerService.modifyUrl(Mockito.anyString(), Mockito.any(UrlAlias.class))).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));


            mockMvc.perform(put("/abcd1234")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mockRequestContent))
                    .andExpect(status().isNotFound());
        }

        @Test
        void shouldReturnBadRequestIfBodyIsNotValid() throws Exception {
            String mockInvalidRequestContent = "adf1981";

            mockMvc.perform(put("/abcd1234")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mockInvalidRequestContent))
                    .andExpect(status().isBadRequest());
        }

    }

    @Nested
    @DisplayName("Tests for getUrl Method")
    class getUrl {
        @Test
        void shouldRedirectIfAliasExistsInDB() throws Exception {
            String mockUrl = "www.google.com";
            RedirectView mockRedirectView = new RedirectView(mockUrl);
            mockRedirectView.setStatusCode(HttpStatus.PERMANENT_REDIRECT);

            Mockito.when(urlShortenerService.getUrl(Mockito.anyString())).thenReturn(mockRedirectView);

            mockMvc.perform(get("/abcd1234")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isPermanentRedirect());
        }

        @Test
        void shouldReturnNotFoundIfResourceNotFound() throws Exception {


            Mockito.when(urlShortenerService.getUrl(Mockito.anyString())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No URL Found."));

            mockMvc.perform(get("/abcd1234")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

    }
}