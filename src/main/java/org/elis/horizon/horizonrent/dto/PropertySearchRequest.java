package org.elis.horizon.horizonrent.dto;

import jakarta.validation.constraints.Min;
import org.elis.horizon.horizonrent.model.PropertyType;

import java.math.BigDecimal;
import java.util.Objects;

public class PropertySearchRequest {
    private String keyword;
    private String city;
    private String state;

    @Min(0)
    private BigDecimal minPrice;

    @Min(0)
    private BigDecimal maxPrice;

    @Min(0)
    private Integer minBedrooms;

    @Min(0)
    private Integer minBathrooms;

    private PropertyType propertyType;

    public PropertySearchRequest() {
    }

    public PropertySearchRequest(String keyword, String city, String state, BigDecimal minPrice, BigDecimal maxPrice, Integer minBedrooms, Integer minBathrooms, PropertyType propertyType) {
        this.keyword = keyword;
        this.city = city;
        this.state = state;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minBedrooms = minBedrooms;
        this.minBathrooms = minBathrooms;
        this.propertyType = propertyType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMinBedrooms() {
        return minBedrooms;
    }

    public void setMinBedrooms(Integer minBedrooms) {
        this.minBedrooms = minBedrooms;
    }

    public Integer getMinBathrooms() {
        return minBathrooms;
    }

    public void setMinBathrooms(Integer minBathrooms) {
        this.minBathrooms = minBathrooms;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertySearchRequest that = (PropertySearchRequest) o;
        return Objects.equals(keyword, that.keyword) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(minPrice, that.minPrice) && Objects.equals(maxPrice, that.maxPrice) && Objects.equals(minBedrooms, that.minBedrooms) && Objects.equals(minBathrooms, that.minBathrooms) && propertyType == that.propertyType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyword, city, state, minPrice, maxPrice, minBedrooms, minBathrooms, propertyType);
    }

    @Override
    public String toString() {
        return "PropertySearchRequest{" +
               "keyword='" + keyword + "'" +
               ", city='" + city + "'" +
               ", state='" + state + "'" +
               ", minPrice=" + minPrice +
               ", maxPrice=" + maxPrice +
               ", minBedrooms=" + minBedrooms +
               ", minBathrooms=" + minBathrooms +
               ", propertyType=" + propertyType +
               '}';
    }
}
