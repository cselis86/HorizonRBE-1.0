package org.elis.horizon.horizonrent.controller;

import org.elis.horizon.horizonrent.dto.PagedResponse;
import org.elis.horizon.horizonrent.dto.PropertyDetailResponse;
import org.elis.horizon.horizonrent.dto.PropertyListResponse;
import org.elis.horizon.horizonrent.dto.PropertySearchRequest;
import org.elis.horizon.horizonrent.exception.InvalidParameterException;
import org.elis.horizon.horizonrent.exception.PropertyNotFoundException;
import org.elis.horizon.horizonrent.service.PropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

/**
 * REST controller for property-related operations.
 * Provides endpoints for listing, searching, and retrieving property details.
 */
@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*") // Configure appropriately for production
public class PropertyController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);
    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    /**
     * Get all available properties with pagination and sorting.
     *
     * @param page Page number (0-indexed)
     * @param size Number of items per page
     * @param sortBy Field to sort by (price, createdAt, bedrooms)
     * @param sortOrder Sort order (asc, desc)
     * @return Paginated list of properties
     */
    @GetMapping
    public ResponseEntity<PagedResponse<PropertyListResponse>> getAllProperties(
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {

        logger.info("Fetching properties - page: {}, size: {}, sortBy: {}, sortOrder: {}",
                page, size, sortBy, sortOrder);

        // Validate sortBy and sortOrder
        if (!isValidSortField(sortBy)) {
            throw new InvalidParameterException("Invalid sort field: " + sortBy);
        }

        if (!sortOrder.equalsIgnoreCase("asc") && !sortOrder.equalsIgnoreCase("desc")) {
            throw new InvalidParameterException("Sort order must be 'asc' or 'desc'");
        }

        PagedResponse<PropertyListResponse> response =
                propertyService.getAllProperties(page, size, sortBy, sortOrder);

        return ResponseEntity.ok(response);
    }

    /**
     * Get detailed information about a specific property.
     *
     * @param id Property ID
     * @return Detailed property information
     */
    @GetMapping("/{id}")
    public ResponseEntity<PropertyDetailResponse> getPropertyById(
            @PathVariable @Min(1) Long id) {

        logger.info("Fetching property details for ID: {}", id);

        PropertyDetailResponse property = propertyService.getPropertyById(id);

        if (property == null) {
            throw new PropertyNotFoundException("Property with ID " + id + " not found");
        }

        logger.info("Successfully retrieved property: {}", property.getTitle());

        return ResponseEntity.ok(property);
    }

    /**
     * Search properties based on multiple criteria.
     *
     * @param searchRequest Search criteria
     * @param page Page number
     * @param size Page size
     * @return Filtered and paginated property list
     */
    @PostMapping("/search")
    public ResponseEntity<PagedResponse<PropertyListResponse>> searchProperties(
            @Valid @RequestBody PropertySearchRequest searchRequest,
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size) {

        logger.info("Searching properties with criteria: {}", searchRequest);
        logger.debug("Search details - keyword: {}, city: {}, minPrice: {}, maxPrice: {}, minBedrooms: {}",
                searchRequest.getKeyword(), searchRequest.getCity(),
                searchRequest.getMinPrice(), searchRequest.getMaxPrice(),
                searchRequest.getMinBedrooms());

        // Validate search parameters
        validateSearchRequest(searchRequest);

        PagedResponse<PropertyListResponse> response =
                propertyService.searchProperties(searchRequest, page, size);

        logger.info("Search returned {} results out of {} total",
                response.getContent().size(), response.getTotalElements());

        return ResponseEntity.ok(response);
    }

    /**
     * Get featured properties for homepage display.
     * Returns the newest available properties.
     *
     * @param limit Maximum number of properties to return
     * @return List of featured properties
     */
    @GetMapping("/featured")
    public ResponseEntity<List<PropertyListResponse>> getFeaturedProperties(
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) Integer limit) {

        logger.info("Fetching {} featured properties", limit);

        List<PropertyListResponse> properties = propertyService.getFeaturedProperties(limit);

        logger.info("Returning {} featured properties", properties.size());

        return ResponseEntity.ok(properties);
    }

    private void validateSearchRequest(PropertySearchRequest request) {
        if (request.getMinPrice() != null && request.getMaxPrice() != null) {
            if (request.getMinPrice().compareTo(request.getMaxPrice()) > 0) {
                throw new InvalidParameterException("minPrice cannot be greater than maxPrice");
            }
        }

        if (request.getMinBedrooms() != null && request.getMinBedrooms() < 0) {
            throw new InvalidParameterException("minBedrooms cannot be negative");
        }

        if (request.getMinSquareFeet() != null && request.getMaxSquareFeet() != null) {
            if (request.getMinSquareFeet() > request.getMaxSquareFeet()) {
                throw new InvalidParameterException("minSquareFeet cannot be greater than maxSquareFeet");
            }
        }
    }

    private boolean isValidSortField(String sortBy) {
        return List.of("price", "createdAt", "bedrooms", "squareFeet").contains(sortBy);
    }
}
