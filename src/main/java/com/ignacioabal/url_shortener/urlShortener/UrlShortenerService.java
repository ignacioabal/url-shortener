package com.ignacioabal.url_shortener.urlShortener;

import org.springframework.http.ResponseEntity;

public interface UrlShortenerService {

    ResponseEntity<UrlAlias> createUrl(UrlAlias urlAlias);

    ResponseEntity<UrlAlias> deleteUrl(String alias);

    ResponseEntity<UrlAlias> modifyUrl(String alias, UrlAlias Url);

    ResponseEntity<UrlAlias> getUrl(String alias);

}
