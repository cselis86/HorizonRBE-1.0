package org.elis.horizon.horizonrent.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elis.horizon.horizonrent.model.PropertyType;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertySearchRequest {

    private String keyword;
    private String city;
    private String state;

    @Min(value = 0, message = "Minimum price cannot be negative")
    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    @Min(value = 0, message = "Minimum bedrooms cannot be negative")
    private Integer minBedrooms;

    private Integer maxBedrooms;

    private PropertyType propertyType;

    @Min(value = 0, message = "Minimum square feet cannot be negative")
    private Integer minSquareFeet;

    private Integer maxSquareFeet;

    private List<String> amenities;

    private String sortBy = "createdAt";
    private String sortOrder = "desc";
}