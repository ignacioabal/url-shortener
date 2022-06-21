package com.ignacioabal.url_shortener.urlShortener;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;

public interface UrlShortenerService {

    ResponseEntity<UrlAlias> createUrl(UrlAlias urlAlias);

    ResponseEntity<UrlAlias> deleteUrl(String alias);

    ResponseEntity<UrlAlias> modifyUrl(String alias, UrlAlias Url);

    RedirectView getUrl(String alias);

}
