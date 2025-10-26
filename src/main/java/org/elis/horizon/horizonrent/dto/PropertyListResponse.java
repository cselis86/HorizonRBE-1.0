package org.elis.horizon.horizonrent.dto;

import org.elis.horizon.horizonrent.model.PropertyStatus;
import org.elis.horizon.horizonrent.model.PropertyType;

import java.math.BigDecimal;
import java.util.Objects;

public class PropertyListResponse {
    private Long id;
    private String title;
    private String city;
    private String state;
    private BigDecimal price;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer squareFeet;
    private PropertyType propertyType;
    private String primaryImageUrl;
    private PropertyStatus status;

    public PropertyListResponse() {
    }

    public PropertyListResponse(Long id, String title, String city, String state, BigDecimal price, Integer bedrooms, Integer bathrooms, Integer squareFeet, PropertyType propertyType, String primaryImageUrl, PropertyStatus status) {
        this.id = id;
        this.title = title;
        this.city = city;
        this.state = state;
        this.price = price;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.squareFeet = squareFeet;
        this.propertyType = propertyType;
        this.primaryImageUrl = primaryImageUrl;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Integer getSquareFeet() {
        return squareFeet;
    }

    public void setSquareFeet(Integer squareFeet) {
        this.squareFeet = squareFeet;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public String getPrimaryImageUrl() {
        return primaryImageUrl;
    }

    public void setPrimaryImageUrl(String primaryImageUrl) {
        this.primaryImageUrl = primaryImageUrl;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyListResponse that = (PropertyListResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(price, that.price) && Objects.equals(bedrooms, that.bedrooms) && Objects.equals(bathrooms, that.bathrooms) && Objects.equals(squareFeet, that.squareFeet) && propertyType == that.propertyType && Objects.equals(primaryImageUrl, that.primaryImageUrl) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, city, state, price, bedrooms, bathrooms, squareFeet, propertyType, primaryImageUrl, status);
    }

    @Override
    public String toString() {
        return "PropertyListResponse{"
               + "id=" + id + 
               ", title='" + title + "'" +
               ", city='" + city + "'" +
               ", state='" + state + "'" +
               ", price=" + price + 
               ", bedrooms=" + bedrooms + 
               ", bathrooms=" + bathrooms + 
               ", squareFeet=" + squareFeet + 
               ", propertyType=" + propertyType + 
               ", primaryImageUrl='" + primaryImageUrl + "'" +
               ", status=" + status + 
               '}';
    }
}
