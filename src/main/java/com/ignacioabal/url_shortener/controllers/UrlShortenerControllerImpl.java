package com.ignacioabal.url_shortener.controllers;

import com.ignacioabal.url_shortener.models.UrlAlias;
import com.ignacioabal.url_shortener.services.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("")
public class UrlShortenerControllerImpl implements UrlShortenerController {

    @Autowired
    UrlShortenerService urlShortenerService;

    @Override
    @PostMapping()
    public ResponseEntity<UrlAlias> createUrl(@RequestBody UrlAlias urlAlias) {
        return urlShortenerService.createUrl(urlAlias);
    }

    @Override
    @DeleteMapping("/{alias}")
    public ResponseEntity<UrlAlias> deleteUrl(@PathVariable("alias") String alias) {
        return urlShortenerService.deleteUrl(alias);
    }

    @Override
    @PutMapping("/{alias}")
    public ResponseEntity<UrlAlias> modifyUrl(@PathVariable("alias") String alias, @RequestBody UrlAlias url) {

        return urlShortenerService.modifyUrl(alias, url);
    }

    @Override
    @GetMapping("/{alias}")
    public RedirectView getUrl(@PathVariable("alias") String alias, HttpServletRequest request) {
        RedirectView redirectView = new RedirectView();

        String url = urlShortenerService.getUrl(alias);
        if (url == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No URL Found.");

        String redirectUrl = "https://" + url;

        redirectView.setUrl(redirectUrl);

        return redirectView;
    }
}
