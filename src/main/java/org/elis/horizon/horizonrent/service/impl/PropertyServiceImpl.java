package org.elis.horizon.horizonrent.service.impl;

import org.elis.horizon.horizonrent.dto.PagedResponse;
import org.elis.horizon.horizonrent.dto.PropertyDetailResponse;
import org.elis.horizon.horizonrent.dto.PropertyListResponse;
import org.elis.horizon.horizonrent.dto.PropertySearchRequest;
import org.elis.horizon.horizonrent.model.Property;
import org.elis.horizon.horizonrent.repository.PropertyRepository;
import org.elis.horizon.horizonrent.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public PagedResponse<PropertyListResponse> getAllProperties(int page, int size, String sortBy, String sortOrder) {
        List<Property> allProperties = propertyRepository.findAll();

        // Apply sorting
        Comparator<Property> comparator = getPropertyComparator(sortBy, sortOrder);
        allProperties.sort(comparator);

        // Apply pagination
        int start = (int) PageRequest.of(page, size).getOffset();
        int end = Math.min((start + size), allProperties.size());
        List<Property> pagedProperties = allProperties.subList(start, end);

        List<PropertyListResponse> content = pagedProperties.stream()
                .map(this::convertToPropertyListResponse)
                .collect(Collectors.toList());

        return PagedResponse.of(content, allProperties.size(), page, size);
    }

    @Override
    public PropertyDetailResponse getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .map(this::convertToPropertyDetailResponse)
                .orElse(null);
    }

    @Override
    public PagedResponse<PropertyListResponse> searchProperties(PropertySearchRequest searchRequest, int page, int size) {
        List<Property> filteredProperties = propertyRepository.searchByCriteria(
                searchRequest.getKeyword(),
                searchRequest.getCity(),
                searchRequest.getMinPrice(),
                searchRequest.getMaxPrice(),
                searchRequest.getMinBedrooms(),
                searchRequest.getMaxBedrooms(), // Assuming maxBedrooms is added to searchByCriteria
                searchRequest.getPropertyType()
        );

        // Apply sorting from searchRequest
        Comparator<Property> comparator = getPropertyComparator(searchRequest.getSortBy(), searchRequest.getSortOrder());
        filteredProperties.sort(comparator);

        // Apply pagination
        int start = (int) PageRequest.of(page, size).getOffset();
        int end = Math.min((start + size), filteredProperties.size());
        List<Property> pagedProperties = filteredProperties.subList(start, end);

        List<PropertyListResponse> content = pagedProperties.stream()
                .map(this::convertToPropertyListResponse)
                .collect(Collectors.toList());

        return PagedResponse.of(content, filteredProperties.size(), page, size);
    }

    @Override
    public List<PropertyListResponse> getFeaturedProperties(Integer limit) {
        return propertyRepository.findAll().stream()
                .sorted(Comparator.comparing(Property::getCreatedAt).reversed()) // Newest properties first
                .limit(limit)
                .map(this::convertToPropertyListResponse)
                .collect(Collectors.toList());
    }

    private PropertyListResponse convertToPropertyListResponse(Property property) {
        // This is a simplified conversion. In a real app, you'd use a dedicated mapper.
        PropertyListResponse dto = new PropertyListResponse();
        dto.setId(property.getId());
        dto.setTitle(property.getTitle());
        dto.setCity(property.getAddress() != null ? property.getAddress().getCity() : null);
        dto.setState(property.getAddress() != null ? property.getAddress().getState() : null);
        dto.setPrice(property.getPrice());
        dto.setBedrooms(property.getBedrooms() != null ? property.getBedrooms() : 0);
        dto.setBathrooms(property.getBathrooms() != null ? property.getBathrooms() : 0);
        dto.setSquareFeet(property.getSquareFeet());
        dto.setPropertyType(property.getPropertyType());
        // Assuming the first image is the primary one
        dto.setPrimaryImageUrl(property.getImages() != null && !property.getImages().isEmpty() ? property.getImages().get(0).getUrl() : null);
        dto.setStatus(property.getStatus());
        return dto;
    }

    private PropertyDetailResponse convertToPropertyDetailResponse(Property property) {
        // This is a simplified conversion. In a real app, you'd use a dedicated mapper.
        PropertyDetailResponse dto = new PropertyDetailResponse();
        dto.setId(property.getId());
        dto.setTitle(property.getTitle());
        dto.setDescription(property.getDescription());
        dto.setAddress(property.getAddress());
        dto.setPrice(property.getPrice());
        dto.setBedrooms(property.getBedrooms() != null ? property.getBedrooms() : 0);
        dto.setBathrooms(property.getBathrooms() != null ? property.getBathrooms() : 0);
        dto.setSquareFeet(property.getSquareFeet());
        dto.setPropertyType(property.getPropertyType());
        dto.setStatus(property.getStatus());
        dto.setAmenities(property.getAmenities());
        dto.setImages(property.getImages());
        dto.setCreatedAt(property.getCreatedAt());
        dto.setUpdatedAt(property.getUpdatedAt());
        return dto;
    }

    private Comparator<Property> getPropertyComparator(String sortBy, String sortOrder) {
        Comparator<Property> comparator;
        switch (sortBy.toLowerCase()) {
            case "price":
                comparator = Comparator.comparing(Property::getPrice, Comparator.nullsLast(BigDecimal::compareTo));
                break;
            case "bedrooms":
                comparator = Comparator.comparing(Property::getBedrooms, Comparator.nullsLast(Integer::compareTo));
                break;
            case "squarefeet":
                comparator = Comparator.comparing(Property::getSquareFeet, Comparator.nullsLast(Integer::compareTo));
                break;
            default: // createdAt
                comparator = Comparator.comparing(Property::getCreatedAt, Comparator.nullsLast(LocalDateTime::compareTo));
                break;
        }

        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}