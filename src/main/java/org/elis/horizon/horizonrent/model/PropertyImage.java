package org.elis.horizon.horizonrent.model;

import java.util.Objects;

public class PropertyImage {
    private String url;
    private String description;

    public PropertyImage() {
    }

    public PropertyImage(String url, String description) {
        this.url = url;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyImage that = (PropertyImage) o;
        return Objects.equals(url, that.url) &&
               Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, description);
    }

    @Override
    public String toString() {
        return "PropertyImage{" +
               "url='" + url + "'" +
               ", description='" + description + "'" +
               '}';
    }
}
