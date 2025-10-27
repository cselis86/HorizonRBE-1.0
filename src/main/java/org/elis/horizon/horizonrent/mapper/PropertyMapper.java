package org.elis.horizon.horizonrent.mapper;

import org.elis.horizon.horizonrent.dto.PropertyDetailResponse;
import org.elis.horizon.horizonrent.dto.PropertyListResponse;
import org.elis.horizon.horizonrent.model.Property;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class PropertyMapper {

    public PropertyListResponse toListResponse(Property property) {
        if (property == null) {
            return null;
        }
        String primaryImageUrl = Optional.ofNullable(property.getImages())
                .orElse(Collections.emptyList())
                .stream()
                .findFirst()
                .map(image -> image.getUrl())
                .orElse(null);

        return new PropertyListResponse(
                property.getId(),
                property.getTitle(),
                property.getAddress() != null ? property.getAddress().getCity() : null,
                property.getAddress() != null ? property.getAddress().getState() : null,
                property.getPrice(),
                property.getBedrooms(),
                property.getBathrooms(),
                property.getSquareFeet(),
                property.getPropertyType(),
                primaryImageUrl,
                property.getStatus()
        );
    }

    public PropertyDetailResponse toDetailResponse(Property property) {
        if (property == null) {
            return null;
        }
        return new PropertyDetailResponse(
                property.getId(),
                property.getTitle(),
                property.getDescription(),
                property.getAddress(),
                property.getPrice(),
                property.getBedrooms(),
                property.getBathrooms(),
                property.getSquareFeet(),
                property.getPropertyType(),
                property.getStatus(),
                property.getAmenities(),
                property.getImages(),
                property.getCreatedAt(),
                property.getUpdatedAt()
        );
    }
}
