package org.elis.horizon.horizonrent.repository;

import org.elis.horizon.horizonrent.model.Address;
import org.elis.horizon.horizonrent.model.Property;
import org.elis.horizon.horizonrent.model.PropertyImage;
import org.elis.horizon.horizonrent.model.PropertyStatus;
import org.elis.horizon.horizonrent.model.PropertyType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryPropertyRepository implements PropertyRepository {

    private final ConcurrentHashMap<Long, Property> properties = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public InMemoryPropertyRepository() {
        // Pre-populate with sample data
        save(new Property(
                null,
                "Modern Downtown Loft",
                "Beautiful apartment in the heart of downtown with stunning city views.",
                new Address("123 Main St", "Austin", "TX", "78701", "USA"),
                new BigDecimal("2800.00"),
                2,
                2,
                1200,
                PropertyType.APARTMENT,
                PropertyStatus.AVAILABLE,
                Arrays.asList("Gym", "Parking", "Pet Friendly"),
                Arrays.asList(new PropertyImage("https://example.com/apt1_1.jpg", "Living Room"), new PropertyImage("https://example.com/apt1_2.jpg", "Kitchen")),
                101L,
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now()
        ));
        save(new Property(
                null,
                "Spacious Family House",
                "A lovely family home with a big yard in a quiet neighborhood.",
                new Address("456 Oak Ave", "Seattle", "WA", "98101", "USA"),
                new BigDecimal("550000.00"),
                4,
                3,
                2500,
                PropertyType.HOUSE,
                PropertyStatus.AVAILABLE,
                Arrays.asList("Garden", "Garage", "Fireplace"),
                Arrays.asList(new PropertyImage("https://example.com/house1_1.jpg", "Exterior"), new PropertyImage("https://example.com/house1_2.jpg", "Backyard")),
                102L,
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now().minusDays(2)
        ));
        save(new Property(
                null,
                "Cozy Condo near the Beach",
                "Perfect for a single person or a couple, close to the beach and local shops.",
                new Address("789 Pine Ln", "Miami", "FL", "33101", "USA"),
                new BigDecimal("3500.00"),
                1,
                1,
                750,
                PropertyType.CONDO,
                PropertyStatus.RENTED,
                Arrays.asList("Pool", "Balcony"),
                Arrays.asList(new PropertyImage("https://example.com/condo1_1.jpg", "Bedroom"), new PropertyImage("https://example.com/condo1_2.jpg", "Pool View")),
                103L,
                LocalDateTime.now().minusDays(15),
                LocalDateTime.now().minusDays(7)
        ));
    }

    @Override
    public Property save(Property property) {
        if (property.getId() == null) {
            property.setId(idGenerator.incrementAndGet());
            property.setCreatedAt(LocalDateTime.now());
        }
        property.setUpdatedAt(LocalDateTime.now());
        properties.put(property.getId(), property);
        return property;
    }

    @Override
    public Optional<Property> findById(Long id) {
        return Optional.ofNullable(properties.get(id));
    }

    @Override
    public List<Property> findAll() {
        return new ArrayList<>(properties.values());
    }

    @Override
    public void deleteById(Long id) {
        properties.remove(id);
    }

    @Override
    public List<Property> findByStatus(PropertyStatus status) {
        return properties.values().stream()
                .filter(p -> p.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<Property> findByCity(String city) {
        return properties.values().stream()
                .filter(p -> p.getAddress() != null && p.getAddress().getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    @Override
    public List<Property> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return properties.values().stream()
                .filter(p -> p.getPrice() != null &&
                        (minPrice == null || p.getPrice().compareTo(minPrice) >= 0) &&
                        (maxPrice == null || p.getPrice().compareTo(maxPrice) <= 0))
                .collect(Collectors.toList());
    }

    @Override
    public List<Property> searchByCriteria(String keyword, String city, BigDecimal minPrice, BigDecimal maxPrice, Integer minBedrooms, Integer minBathrooms, PropertyType propertyType) {
        String lowerCaseKeyword = keyword != null ? keyword.toLowerCase(Locale.ROOT) : null;

        return properties.values().stream()
                .filter(p -> {
                    boolean matches = true;

                    // Keyword search (title or description)
                    if (lowerCaseKeyword != null) {
                        matches = (p.getTitle() != null && p.getTitle().toLowerCase(Locale.ROOT).contains(lowerCaseKeyword)) ||
                                  (p.getDescription() != null && p.getDescription().toLowerCase(Locale.ROOT).contains(lowerCaseKeyword));
                    }

                    // City filter
                    if (matches && city != null) {
                        matches = p.getAddress() != null && p.getAddress().getCity().equalsIgnoreCase(city);
                    }

                    // Price range filter
                    if (matches && (minPrice != null || maxPrice != null)) {
                        matches = p.getPrice() != null &&
                                (minPrice == null || p.getPrice().compareTo(minPrice) >= 0) &&
                                (maxPrice == null || p.getPrice().compareTo(maxPrice) <= 0);
                    }

                    // Min bedrooms filter
                    if (matches && minBedrooms != null) {
                        matches = p.getBedrooms() != null && p.getBedrooms() >= minBedrooms;
                    }

                    // Min bathrooms filter
                    if (matches && minBathrooms != null) {
                        matches = p.getBathrooms() != null && p.getBathrooms() >= minBathrooms;
                    }

                    // Property type filter
                    if (matches && propertyType != null) {
                        matches = p.getPropertyType() == propertyType;
                    }

                    return matches;
                })
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return properties.size();
    }
}