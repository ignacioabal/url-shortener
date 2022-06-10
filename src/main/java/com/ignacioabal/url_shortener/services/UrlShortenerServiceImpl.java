package com.ignacioabal.url_shortener.services;

import com.ignacioabal.url_shortener.models.UrlAlias;
import com.ignacioabal.url_shortener.repositories.UrlShortenerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Autowired
    UrlShortenerRepository urlShortenerRepository;

    @Override
    public ResponseEntity<UrlAlias> createUrl(UrlAlias urlAlias) {
        String generatedAlias = generateAlias();

        urlAlias.setAlias(generatedAlias);

        while (urlShortenerRepository.existsByAlias(generatedAlias)) {
            generatedAlias = generateAlias();

            urlAlias.setUrl(generatedAlias);
        }

        urlShortenerRepository.save(urlAlias);
        return new ResponseEntity<>(urlAlias, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UrlAlias> deleteUrl(String alias) {
        if (!urlShortenerRepository.existsByAlias(alias)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        urlShortenerRepository.deleteUrlByAlias(alias);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UrlAlias> modifyUrl(String alias, UrlAlias newUrl) {
        Optional<UrlAlias> optionalUrlAlias = urlShortenerRepository.findUrlByAlias(alias);

        if (optionalUrlAlias.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


        UrlAlias existingUrlAlias = optionalUrlAlias.get();

        existingUrlAlias.setUrl(newUrl.getUrl());
        urlShortenerRepository.save(existingUrlAlias);
        return new ResponseEntity<>(existingUrlAlias, HttpStatus.OK);

    }


    @Override
    public RedirectView getUrl(String alias) {
        Optional<UrlAlias> urlAlias = urlShortenerRepository.findUrlByAlias(alias);

        if (urlAlias.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No URL Found.");
        }

        RedirectView redirectView = new RedirectView(urlAlias.get().getUrl());
        redirectView.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
        return redirectView;
    }

    private String generateAlias() {
        final int length = 10;
        final boolean useLetters = true;
        final boolean useNumbers = true;

        return RandomStringUtils.random(length, useLetters, useNumbers);
    }


}
