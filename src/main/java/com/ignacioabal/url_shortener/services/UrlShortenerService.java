package com.ignacioabal.url_shortener.services;

import com.ignacioabal.url_shortener.models.UrlAlias;
import org.springframework.http.ResponseEntity;

public interface UrlShortenerService {

    ResponseEntity<UrlAlias> createUrl(UrlAlias urlAlias);

    ResponseEntity<UrlAlias> deleteUrl(String alias);

    ResponseEntity<UrlAlias> modifyUrl(String alias, UrlAlias Url);

    String getUrl(String alias);

}
