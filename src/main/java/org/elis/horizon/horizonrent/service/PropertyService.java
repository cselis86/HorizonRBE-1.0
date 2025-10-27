package org.elis.horizon.horizonrent.service;

import org.elis.horizon.horizonrent.dto.PagedResponse;
import org.elis.horizon.horizonrent.dto.PropertyDetailResponse;
import org.elis.horizon.horizonrent.dto.PropertyListResponse;
import org.elis.horizon.horizonrent.dto.PropertySearchRequest;

public interface PropertyService {
    PagedResponse<PropertyListResponse> getAllProperties(int page, int size, String sortBy, String sortOrder);
    PropertyDetailResponse getPropertyById(Long id);
    PagedResponse<PropertyListResponse> searchProperties(PropertySearchRequest searchRequest, int page, int size);
}