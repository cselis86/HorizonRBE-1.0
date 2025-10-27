package org.elis.horizon.horizonrent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elis.horizon.horizonrent.model.PropertyStatus;
import org.elis.horizon.horizonrent.model.PropertyType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyListResponse {
    private Long id;
    private String title;
    private String city;
    private String state;
    private BigDecimal price;
    private int bedrooms;
    private int bathrooms;
    private int squareFeet;
    private PropertyType propertyType;
    private String primaryImageUrl;
    private PropertyStatus status;
}