package com.ignacioabal.url_shortener.urlShortener;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

public interface UrlShortenerController {
    ResponseEntity<UrlAlias> createUrl(UrlAlias urlAlias);

    ResponseEntity<UrlAlias> deleteUrl(String alias);

    ResponseEntity<UrlAlias> modifyUrl(String alias, UrlAlias url);

    RedirectView getUrl(String alias, HttpServletRequest request);
}
