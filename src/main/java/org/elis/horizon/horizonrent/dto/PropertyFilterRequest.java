package org.elis.horizon.horizonrent.dto;

import jakarta.validation.constraints.Min;

import java.util.List;
import java.util.Objects;

public class PropertyFilterRequest {
    private List<String> amenities;

    @Min(0)
    private Integer minSquareFeet;

    @Min(0)
    private Integer maxSquareFeet;

    @Min(0)
    private Integer bathrooms;

    public PropertyFilterRequest() {
    }

    public PropertyFilterRequest(List<String> amenities, Integer minSquareFeet, Integer maxSquareFeet, Integer bathrooms) {
        this.amenities = amenities;
        this.minSquareFeet = minSquareFeet;
        this.maxSquareFeet = maxSquareFeet;
        this.bathrooms = bathrooms;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public Integer getMinSquareFeet() {
        return minSquareFeet;
    }

    public void setMinSquareFeet(Integer minSquareFeet) {
        this.minSquareFeet = minSquareFeet;
    }

    public Integer getMaxSquareFeet() {
        return maxSquareFeet;
    }

    public void setMaxSquareFeet(Integer maxSquareFeet) {
        this.maxSquareFeet = maxSquareFeet;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyFilterRequest that = (PropertyFilterRequest) o;
        return Objects.equals(amenities, that.amenities) && Objects.equals(minSquareFeet, that.minSquareFeet) && Objects.equals(maxSquareFeet, that.maxSquareFeet) && Objects.equals(bathrooms, that.bathrooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amenities, minSquareFeet, maxSquareFeet, bathrooms);
    }

    @Override
    public String toString() {
        return "PropertyFilterRequest{" +
               "amenities=" + amenities +
               ", minSquareFeet=" + minSquareFeet +
               ", maxSquareFeet=" + maxSquareFeet +
               ", bathrooms=" + bathrooms +
               '}';
    }
}
