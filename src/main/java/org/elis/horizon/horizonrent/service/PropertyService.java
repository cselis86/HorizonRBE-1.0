package org.elis.horizon.horizonrent.service;

import org.elis.horizon.horizonrent.dto.PagedResponse;
import org.elis.horizon.horizonrent.dto.PropertyDetailResponse;
import org.elis.horizon.horizonrent.dto.PropertyListResponse;

public interface PropertyService {
    PagedResponse<PropertyListResponse> getAllProperties(int page, int size, String sortBy, String sortOrder);
    PropertyDetailResponse getPropertyById(Long id);
}