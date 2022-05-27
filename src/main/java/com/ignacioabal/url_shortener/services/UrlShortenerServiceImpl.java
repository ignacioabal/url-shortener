package com.ignacioabal.url_shortener.services;

import com.ignacioabal.url_shortener.models.UrlAlias;
import com.ignacioabal.url_shortener.repositories.UrlShortenerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Autowired
    UrlShortenerRepository urlShortenerRepository;

    @Override
    public ResponseEntity<UrlAlias> createUrl(UrlAlias urlAlias) {
        String generatedAlias = generateAlias();

        urlAlias.setAlias(generatedAlias);
        urlShortenerRepository.save(urlAlias);
        return new ResponseEntity<>(urlAlias, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UrlAlias> deleteUrl(String alias) {
        try {
            urlShortenerRepository.deleteUrlByAlias(alias);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UrlAlias> modifyUrl(String alias, UrlAlias newUrl) {
        Optional<UrlAlias> existingUrlAlias = urlShortenerRepository.findUrlByAlias(alias);

        if (existingUrlAlias.isPresent()) {
            existingUrlAlias.get().setUrl(newUrl.getUrl());
            urlShortenerRepository.save(existingUrlAlias.get());
            return new ResponseEntity<>(existingUrlAlias.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public String getUrl(String alias) {
        Optional<UrlAlias> urlAlias = urlShortenerRepository.findUrlByAlias(alias);

        if (urlAlias.isPresent()) {
            return urlAlias.get().getUrl();
        } else {
            return null;
        }

    }

    private String generateAlias() {
        final int length = 10;
        final boolean useLetters = true;
        final boolean useNumbers = true;

        return RandomStringUtils.random(length, useLetters, useNumbers);
    }


}
