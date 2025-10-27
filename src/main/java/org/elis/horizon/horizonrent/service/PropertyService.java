package org.elis.horizon.horizonrent.service;

import org.elis.horizon.horizonrent.dto.PagedResponse;
import org.elis.horizon.horizonrent.dto.PropertyDetailResponse;
import org.elis.horizon.horizonrent.dto.PropertyFilterRequest;
import org.elis.horizon.horizonrent.dto.PropertyListResponse;
import org.elis.horizon.horizonrent.dto.PropertySearchRequest;
import org.elis.horizon.horizonrent.exception.PropertyNotFoundException;
import org.elis.horizon.horizonrent.mapper.PropertyMapper;
import org.elis.horizon.horizonrent.model.Property;
import org.elis.horizon.horizonrent.model.PropertyStatus;
import org.elis.horizon.horizonrent.repository.PropertyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

    public PropertyService(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    public PagedResponse<PropertyListResponse> getAllProperties(int page, int size, String sortBy, String sortOrder) {
        List<Property> allProperties = propertyRepository.findAll();

        // Sorting
        Comparator<Property> comparator = getComparator(sortBy, sortOrder);
        if (comparator != null) {
            allProperties.sort(comparator);
        }

        // Pagination
        int start = page * size;
        int end = Math.min(start + size, allProperties.size());
        List<Property> pagedProperties = allProperties.subList(start, end);

        List<PropertyListResponse> content = pagedProperties.stream()
                .map(propertyMapper::toListResponse)
                .collect(Collectors.toList());

        long totalElements = propertyRepository.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PagedResponse<>(content, page, size, totalElements, totalPages, (page + 1) * size >= totalElements);
    }

    public PropertyDetailResponse getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found with id: " + id));
        return propertyMapper.toDetailResponse(property);
    }

    public PagedResponse<PropertyListResponse> searchProperties(PropertySearchRequest request, int page, int size, String sortBy, String sortOrder) {
        List<Property> filteredProperties = propertyRepository.searchByCriteria(
                request.getKeyword(),
                request.getCity(),
                request.getMinPrice(),
                request.getMaxPrice(),
                request.getMinBedrooms(),
                request.getMinBathrooms(),
                request.getPropertyType()
        );

        // Apply additional filters from PropertyFilterRequest if needed (not directly in searchByCriteria yet)
        // For now, assuming PropertySearchRequest covers all search/filter needs.

        // Sorting
        Comparator<Property> comparator = getComparator(sortBy, sortOrder);
        if (comparator != null) {
            filteredProperties.sort(comparator);
        }

        // Pagination
        int start = page * size;
        int end = Math.min(start + size, filteredProperties.size());
        List<Property> pagedProperties = filteredProperties.subList(start, end);

        List<PropertyListResponse> content = pagedProperties.stream()
                .map(propertyMapper::toListResponse)
                .collect(Collectors.toList());

        long totalElements = filteredProperties.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PagedResponse<>(content, page, size, totalElements, totalPages, (page + 1) * size >= totalElements);
    }

    public PagedResponse<PropertyListResponse> filterProperties(PropertyFilterRequest request, int page, int size, String sortBy, String sortOrder) {
        // This method will be similar to searchProperties but will use PropertyFilterRequest fields.
        // For now, I'll implement a basic version that filters by amenities and square feet.
        List<Property> allProperties = propertyRepository.findAll();

        List<Property> filteredProperties = allProperties.stream()
                .filter(property -> {
                    boolean matches = true;

                    // Amenities filter
                    if (request.getAmenities() != null && !request.getAmenities().isEmpty()) {
                        matches = property.getAmenities() != null && property.getAmenities().containsAll(request.getAmenities());
                    }

                    // Min square feet filter
                    if (matches && request.getMinSquareFeet() != null) {
                        matches = property.getSquareFeet() != null && property.getSquareFeet() >= request.getMinSquareFeet();
                    }

                    // Max square feet filter
                    if (matches && request.getMaxSquareFeet() != null) {
                        matches = property.getSquareFeet() != null && property.getSquareFeet() <= request.getMaxSquareFeet();
                    }

                    // Bathrooms filter (exact match for now)
                    if (matches && request.getBathrooms() != null) {
                        matches = property.getBathrooms() != null && property.getBathrooms().equals(request.getBathrooms());
                    }

                    return matches;
                })
                .collect(Collectors.toList());

        // Sorting
        Comparator<Property> comparator = getComparator(sortBy, sortOrder);
        if (comparator != null) {
            filteredProperties.sort(comparator);
        }

        // Pagination
        int start = page * size;
        int end = Math.min(start + size, filteredProperties.size());
        List<Property> pagedProperties = filteredProperties.subList(start, end);

        List<PropertyListResponse> content = pagedProperties.stream()
                .map(propertyMapper::toListResponse)
                .collect(Collectors.toList());

        long totalElements = filteredProperties.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PagedResponse<>(content, page, size, totalElements, totalPages, (page + 1) * size >= totalElements);
    }

    public List<PropertyListResponse> getFeaturedProperties(int limit) {
        // For now, return the newest properties as featured
        return propertyRepository.findAll().stream()
                .sorted(Comparator.comparing(Property::getCreatedAt).reversed())
                .limit(limit)
                .map(propertyMapper::toListResponse)
                .collect(Collectors.toList());
    }

    public long countProperties() {
        return propertyRepository.count();
    }

    private Comparator<Property> getComparator(String sortBy, String sortOrder) {
        Comparator<Property> comparator = null;
        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "price":
                    comparator = Comparator.comparing(Property::getPrice, Comparator.nullsLast(BigDecimal::compareTo));
                    break;
                case "createdat":
                    comparator = Comparator.comparing(Property::getCreatedAt, Comparator.nullsLast(LocalDateTime::compareTo));
                    break;
                case "bedrooms":
                    comparator = Comparator.comparing(Property::getBedrooms, Comparator.nullsLast(Integer::compareTo));
                    break;
                // Add more sorting options as needed
            }
        }

        if (comparator != null && "desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}
