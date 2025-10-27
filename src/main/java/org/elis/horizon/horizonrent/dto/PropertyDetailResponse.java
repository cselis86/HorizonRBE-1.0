package org.elis.horizon.horizonrent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elis.horizon.horizonrent.model.Address;
import org.elis.horizon.horizonrent.model.Amenity;
import org.elis.horizon.horizonrent.model.Image;
import org.elis.horizon.horizonrent.model.PropertyStatus;
import org.elis.horizon.horizonrent.model.PropertyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDetailResponse {
    private Long id;
    private String title;
    private String description;
    private Address address;
    private BigDecimal price;
    private int bedrooms;
    private int bathrooms;
    private int squareFeet;
    private PropertyType propertyType;
    private PropertyStatus status;
    private List<Amenity> amenities;
    private List<Image> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}