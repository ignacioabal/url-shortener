package com.ignacioabal.url_shortener.urlShortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        return urlShortenerService.getUrl(alias);
    }
}
