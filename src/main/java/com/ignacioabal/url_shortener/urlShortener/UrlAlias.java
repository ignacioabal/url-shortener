package com.ignacioabal.url_shortener.urlShortener;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UrlAlias {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UrlAlias urlAlias = (UrlAlias) o;

        if (url != null ? !url.equals(urlAlias.url) : urlAlias.url != null) return false;
        return alias != null ? alias.equals(urlAlias.alias) : urlAlias.alias == null;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        return result;
    }

}
