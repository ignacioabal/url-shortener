package com.ignacioabal.url_shortener.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class UrlAlias {

    @Indexed(unique = true)
    private String url;

    @Id
    @Indexed(unique = true)
    private String alias;

    public UrlAlias() {
    }

    public UrlAlias(String url) {
        this.url = url;
    }

    public UrlAlias(String url, String alias) {
        this.url = url;
        this.alias = alias;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
