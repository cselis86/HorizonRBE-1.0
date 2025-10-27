package org.elis.horizon.horizonrent.service.impl;

import org.elis.horizon.horizonrent.dto.PagedResponse;
import org.elis.horizon.horizonrent.dto.PropertyDetailResponse;
import org.elis.horizon.horizonrent.dto.PropertyListResponse;
import org.elis.horizon.horizonrent.model.Address;
import org.elis.horizon.horizonrent.model.Amenity;
import org.elis.horizon.horizonrent.model.Image;
import org.elis.horizon.horizonrent.model.PropertyStatus;
import org.elis.horizon.horizonrent.model.PropertyType;
import org.elis.horizon.horizonrent.service.PropertyService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Override
    public PagedResponse<PropertyListResponse> getAllProperties(int page, int size, String sortBy, String sortOrder) {
        // Dummy implementation - returns an empty PagedResponse for now
        return PagedResponse.of(Collections.emptyList(), 0, page, size);
    }

    @Override
    public PropertyDetailResponse getPropertyById(Long id) {
        // Dummy implementation
        if (id == 1L) { // Return a dummy property for ID 1
            Address address = new Address("123 Main St", "Austin", "TX", "78701", "USA", 30.2672, -97.7431);
            Amenity amenity1 = new Amenity(1L, "Pool");
            Amenity amenity2 = new Amenity(2L, "Gym");
            Image image1 = new Image(1L, "https://example.com/image1.jpg", "Living Room");
            Image image2 = new Image(2L, "https://example.com/image2.jpg", "Bedroom");

            return new PropertyDetailResponse(
                    1L,
                    "Modern Apartment",
                    "A beautiful modern apartment in downtown Austin.",
                    address,
                    BigDecimal.valueOf(2500),
                    2,
                    2,
                    1200,
                    PropertyType.APARTMENT,
                    PropertyStatus.AVAILABLE,
                    Arrays.asList(amenity1, amenity2),
                    Arrays.asList(image1, image2),
                    LocalDateTime.now().minusDays(5),
                    LocalDateTime.now()
            );
        }
        return null;
    }
}
