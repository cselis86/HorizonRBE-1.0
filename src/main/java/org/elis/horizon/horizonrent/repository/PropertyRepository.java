package org.elis.horizon.horizonrent.repository;

import org.elis.horizon.horizonrent.model.Property;
import org.elis.horizon.horizonrent.model.PropertyStatus;
import org.elis.horizon.horizonrent.model.PropertyType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PropertyRepository {
    Property save(Property property);
    Optional<Property> findById(Long id);
    List<Property> findAll();
    void deleteById(Long id);
    List<Property> findByStatus(PropertyStatus status);
    List<Property> findByCity(String city);
    List<Property> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<Property> searchByCriteria(String keyword, String city, BigDecimal minPrice, BigDecimal maxPrice, Integer minBedrooms, Integer minBathrooms, PropertyType propertyType);
    long count();
}
