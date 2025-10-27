package org.elis.horizon.horizonrent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    private Long id;
    private String title;
    private String description;
    private Address address;
    private BigDecimal price;
    private Integer bedrooms;
    private Integer bathrooms;
    private int squareFeet;
    private PropertyType propertyType;
    private PropertyStatus status;
    private List<Amenity> amenities;
    private List<Image> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
