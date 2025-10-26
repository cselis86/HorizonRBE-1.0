package org.elis.horizon.horizonrent.dto;

import org.elis.horizon.horizonrent.model.Address;
import org.elis.horizon.horizonrent.model.PropertyImage;
import org.elis.horizon.horizonrent.model.PropertyStatus;
import org.elis.horizon.horizonrent.model.PropertyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class PropertyDetailResponse {
    private Long id;
    private String title;
    private String description;
    private Address address;
    private BigDecimal price;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer squareFeet;
    private PropertyType propertyType;
    private PropertyStatus status;
    private List<String> amenities;
    private List<PropertyImage> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PropertyDetailResponse() {
    }

    public PropertyDetailResponse(Long id, String title, String description, Address address, BigDecimal price, Integer bedrooms, Integer bathrooms, Integer squareFeet, PropertyType propertyType, PropertyStatus status, List<String> amenities, List<PropertyImage> images, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.price = price;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.squareFeet = squareFeet;
        this.propertyType = propertyType;
        this.status = status;
        this.amenities = amenities;
        this.images = images;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public List<PropertyImage> getImages() {
        return images;
    }

    public void setImages(List<PropertyImage> images) {
        this.images = images;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyDetailResponse that = (PropertyDetailResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(address, that.address) && Objects.equals(price, that.price) && Objects.equals(bedrooms, that.bedrooms) && Objects.equals(bathrooms, that.bathrooms) && Objects.equals(squareFeet, that.squareFeet) && propertyType == that.propertyType && status == that.status && Objects.equals(amenities, that.amenities) && Objects.equals(images, that.images) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, address, price, bedrooms, bathrooms, squareFeet, propertyType, status, amenities, images, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "PropertyDetailResponse{"
               + "id=" + id + 
               ", title='" + title + "'" +
               ", description='" + description + "'" +
               ", address=" + address + 
               ", price=" + price + 
               ", bedrooms=" + bedrooms + 
               ", bathrooms=" + bathrooms + 
               ", squareFeet=" + squareFeet + 
               ", propertyType=" + propertyType + 
               ", status=" + status + 
               ", amenities=" + amenities + 
               ", images=" + images + 
               ", createdAt=" + createdAt + 
               ", updatedAt=" + updatedAt + 
               '}';
    }
}
