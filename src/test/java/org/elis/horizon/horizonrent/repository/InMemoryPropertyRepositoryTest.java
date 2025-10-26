package org.elis.horizon.horizonrent.repository;

import org.elis.horizon.horizonrent.model.Address;
import org.elis.horizon.horizonrent.model.Property;
import org.elis.horizon.horizonrent.model.PropertyImage;
import org.elis.horizon.horizonrent.model.PropertyStatus;
import org.elis.horizon.horizonrent.model.PropertyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryPropertyRepositoryTest {

    private InMemoryPropertyRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryPropertyRepository();
        // Clear existing data for consistent test results
        repository.findAll().forEach(p -> repository.deleteById(p.getId()));
    }

    private Property createTestProperty(String title, String city, BigDecimal price, PropertyStatus status, PropertyType type) {
        Address address = new Address("123 Test St", city, "CA", "90210", "USA");
        List<String> amenities = Arrays.asList("Pool", "Gym");
        List<PropertyImage> images = Arrays.asList(new PropertyImage("url1", "desc1"));
        return new Property(null, title, "Description for " + title, address, price, 3, 2, 1500, type, status, amenities, images, 1L, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void testSaveNewProperty() {
        Property newProperty = createTestProperty("Test House", "Los Angeles", new BigDecimal("500000"), PropertyStatus.AVAILABLE, PropertyType.HOUSE);
        Property savedProperty = repository.save(newProperty);

        assertNotNull(savedProperty.getId());
        assertEquals("Test House", savedProperty.getTitle());
        assertEquals(1, repository.count());
    }

    @Test
    void testUpdateExistingProperty() {
        Property property = createTestProperty("Original Title", "San Francisco", new BigDecimal("700000"), PropertyStatus.AVAILABLE, PropertyType.CONDO);
        Property savedProperty = repository.save(property);

        savedProperty.setTitle("Updated Title");
        Property updatedProperty = repository.save(savedProperty);

        assertEquals("Updated Title", updatedProperty.getTitle());
        assertEquals(1, repository.count());
    }

    @Test
    void testFindById() {
        Property property = createTestProperty("Find Me", "New York", new BigDecimal("1000000"), PropertyStatus.AVAILABLE, PropertyType.APARTMENT);
        Property savedProperty = repository.save(property);

        Optional<Property> foundProperty = repository.findById(savedProperty.getId());
        assertTrue(foundProperty.isPresent());
        assertEquals("Find Me", foundProperty.get().getTitle());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Property> foundProperty = repository.findById(999L);
        assertFalse(foundProperty.isPresent());
    }

    @Test
    void testFindAll() {
        repository.save(createTestProperty("Prop1", "City1", new BigDecimal("100"), PropertyStatus.AVAILABLE, PropertyType.HOUSE));
        repository.save(createTestProperty("Prop2", "City2", new BigDecimal("200"), PropertyStatus.RENTED, PropertyType.APARTMENT));

        List<Property> allProperties = repository.findAll();
        assertEquals(2, allProperties.size());
    }

    @Test
    void testDeleteById() {
        Property property = createTestProperty("Delete Me", "Boston", new BigDecimal("300000"), PropertyStatus.AVAILABLE, PropertyType.TOWNHOUSE);
        Property savedProperty = repository.save(property);

        repository.deleteById(savedProperty.getId());
        Optional<Property> foundProperty = repository.findById(savedProperty.getId());
        assertFalse(foundProperty.isPresent());
        assertEquals(0, repository.count());
    }

    @Test
    void testCount() {
        assertEquals(0, repository.count());
        repository.save(createTestProperty("Prop1", "City1", new BigDecimal("100"), PropertyStatus.AVAILABLE, PropertyType.HOUSE));
        assertEquals(1, repository.count());
    }

    @Test
    void testFindByStatus() {
        repository.save(createTestProperty("Available1", "CityA", new BigDecimal("100"), PropertyStatus.AVAILABLE, PropertyType.HOUSE));
        repository.save(createTestProperty("Rented1", "CityB", new BigDecimal("200"), PropertyStatus.RENTED, PropertyType.APARTMENT));
        repository.save(createTestProperty("Available2", "CityC", new BigDecimal("300"), PropertyStatus.AVAILABLE, PropertyType.CONDO));

        List<Property> availableProperties = repository.findByStatus(PropertyStatus.AVAILABLE);
        assertEquals(2, availableProperties.size());
        assertTrue(availableProperties.stream().allMatch(p -> p.getStatus() == PropertyStatus.AVAILABLE));

        List<Property> rentedProperties = repository.findByStatus(PropertyStatus.RENTED);
        assertEquals(1, rentedProperties.size());
        assertTrue(rentedProperties.stream().allMatch(p -> p.getStatus() == PropertyStatus.RENTED));
    }

    @Test
    void testFindByCity() {
        repository.save(createTestProperty("PropA", "Dallas", new BigDecimal("100"), PropertyStatus.AVAILABLE, PropertyType.HOUSE));
        repository.save(createTestProperty("PropB", "Houston", new BigDecimal("200"), PropertyStatus.AVAILABLE, PropertyType.APARTMENT));
        repository.save(createTestProperty("PropC", "Dallas", new BigDecimal("300"), PropertyStatus.AVAILABLE, PropertyType.CONDO));

        List<Property> dallasProperties = repository.findByCity("Dallas");
        assertEquals(2, dallasProperties.size());
        assertTrue(dallasProperties.stream().allMatch(p -> p.getAddress().getCity().equals("Dallas")));

        List<Property> austinProperties = repository.findByCity("Austin");
        assertTrue(austinProperties.isEmpty());
    }

    @Test
    void testFindByPriceRange() {
        repository.save(createTestProperty("Cheap", "City", new BigDecimal("100000"), PropertyStatus.AVAILABLE, PropertyType.HOUSE));
        repository.save(createTestProperty("Medium", "City", new BigDecimal("500000"), PropertyStatus.AVAILABLE, PropertyType.APARTMENT));
        repository.save(createTestProperty("Expensive", "City", new BigDecimal("1000000"), PropertyStatus.AVAILABLE, PropertyType.CONDO));

        List<Property> midRange = repository.findByPriceRange(new BigDecimal("200000"), new BigDecimal("600000"));
        assertEquals(1, midRange.size());
        assertEquals("Medium", midRange.get(0).getTitle());

        List<Property> allRange = repository.findByPriceRange(new BigDecimal("50000"), new BigDecimal("1500000"));
        assertEquals(3, allRange.size());

        List<Property> noMatch = repository.findByPriceRange(new BigDecimal("2000000"), new BigDecimal("3000000"));
        assertTrue(noMatch.isEmpty());
    }

    @Test
    void testSearchByCriteria_keyword() {
        repository.save(createTestProperty("Modern Apartment", "City", new BigDecimal("100"), PropertyStatus.AVAILABLE, PropertyType.APARTMENT));
        repository.save(createTestProperty("Cozy House", "City", new BigDecimal("200"), PropertyStatus.AVAILABLE, PropertyType.HOUSE));

        List<Property> results = repository.searchByCriteria("modern", null, null, null, null, null, null);
        assertEquals(1, results.size());
        assertEquals("Modern Apartment", results.get(0).getTitle());
    }

    @Test
    void testSearchByCriteria_city() {
        repository.save(createTestProperty("Prop1", "London", new BigDecimal("100"), PropertyStatus.AVAILABLE, PropertyType.APARTMENT));
        repository.save(createTestProperty("Prop2", "Paris", new BigDecimal("200"), PropertyStatus.AVAILABLE, PropertyType.HOUSE));

        List<Property> results = repository.searchByCriteria(null, "london", null, null, null, null, null);
        assertEquals(1, results.size());
        assertEquals("Prop1", results.get(0).getTitle());
    }

    @Test
    void testSearchByCriteria_priceRange() {
        repository.save(createTestProperty("Cheap", "City", new BigDecimal("100000"), PropertyStatus.AVAILABLE, PropertyType.HOUSE));
        repository.save(createTestProperty("Expensive", "City", new BigDecimal("1000000"), PropertyStatus.AVAILABLE, PropertyType.CONDO));

        List<Property> results = repository.searchByCriteria(null, null, new BigDecimal("50000"), new BigDecimal("200000"), null, null, null);
        assertEquals(1, results.size());
        assertEquals("Cheap", results.get(0).getTitle());
    }

    @Test
    void testSearchByCriteria_minBedrooms() {
        repository.save(new Property(null, "Studio", "", new Address("", "City", "", "", ""), new BigDecimal("100"), 1, 1, 500, PropertyType.APARTMENT, PropertyStatus.AVAILABLE, null, null, 1L, LocalDateTime.now(), LocalDateTime.now()));
        repository.save(new Property(null, "Two Bed", "", new Address("", "City", "", "", ""), new BigDecimal("200"), 2, 1, 1000, PropertyType.APARTMENT, PropertyStatus.AVAILABLE, null, null, 1L, LocalDateTime.now(), LocalDateTime.now()));

        List<Property> results = repository.searchByCriteria(null, null, null, null, 2, null, null);
        assertEquals(1, results.size());
        assertEquals("Two Bed", results.get(0).getTitle());
    }

    @Test
    void testSearchByCriteria_minBathrooms() {
        repository.save(new Property(null, "One Bath", "", new Address("", "City", "", "", ""), new BigDecimal("100"), 1, 1, 500, PropertyType.APARTMENT, PropertyStatus.AVAILABLE, null, null, 1L, LocalDateTime.now(), LocalDateTime.now()));
        repository.save(new Property(null, "Two Bath", "", new Address("", "City", "", "", ""), new BigDecimal("200"), 2, 2, 1000, PropertyType.APARTMENT, PropertyStatus.AVAILABLE, null, null, 1L, LocalDateTime.now(), LocalDateTime.now()));

        List<Property> results = repository.searchByCriteria(null, null, null, null, null, 2, null);
        assertEquals(1, results.size());
        assertEquals("Two Bath", results.get(0).getTitle());
    }

    @Test
    void testSearchByCriteria_propertyType() {
        repository.save(createTestProperty("House1", "City", new BigDecimal("100"), PropertyStatus.AVAILABLE, PropertyType.HOUSE));
        repository.save(createTestProperty("Apartment1", "City", new BigDecimal("200"), PropertyStatus.AVAILABLE, PropertyType.APARTMENT));

        List<Property> results = repository.searchByCriteria(null, null, null, null, null, null, PropertyType.HOUSE);
        assertEquals(1, results.size());
        assertEquals("House1", results.get(0).getTitle());
    }

    @Test
    void testSearchByCriteria_multipleCriteria() {
        repository.save(createTestProperty("Modern Apartment London", "London", new BigDecimal("200000"), PropertyStatus.AVAILABLE, PropertyType.APARTMENT));
        repository.save(createTestProperty("Cozy House Paris", "Paris", new BigDecimal("300000"), PropertyStatus.AVAILABLE, PropertyType.HOUSE));
        repository.save(createTestProperty("Luxury Apartment London", "London", new BigDecimal("250000"), PropertyStatus.AVAILABLE, PropertyType.APARTMENT));

        List<Property> results = repository.searchByCriteria("apartment", "london", new BigDecimal("150000"), new BigDecimal("280000"), 2, 1, PropertyType.APARTMENT);
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(p -> p.getTitle().equals("Modern Apartment London")));
        assertTrue(results.stream().anyMatch(p -> p.getTitle().equals("Luxury Apartment London")));
    }

    @Test
    void testSavePropertyGeneratesIdAndTimestamps() {
        Property newProperty = new Property(null, "New Property", "Desc", new Address("", "", "", "", ""), BigDecimal.ONE, 1, 1, 100, PropertyType.APARTMENT, PropertyStatus.AVAILABLE, null, null, 1L, null, null);
        Property savedProperty = repository.save(newProperty);

        assertNotNull(savedProperty.getId());
        assertNotNull(savedProperty.getCreatedAt());
        assertNotNull(savedProperty.getUpdatedAt());
        assertTrue(savedProperty.getUpdatedAt().isEqual(savedProperty.getCreatedAt()) || savedProperty.getUpdatedAt().isAfter(savedProperty.getCreatedAt()));
    }

    @Test
    void testUpdatePropertyUpdatesTimestamp() throws InterruptedException {
        Property newProperty = new Property(null, "New Property", "Desc", new Address("", "", "", "", ""), BigDecimal.ONE, 1, 1, 100, PropertyType.APARTMENT, PropertyStatus.AVAILABLE, null, null, 1L, null, null);
        Property savedProperty = repository.save(newProperty);
        LocalDateTime initialUpdatedAt = savedProperty.getUpdatedAt();

        Thread.sleep(10);

        savedProperty.setTitle("Updated Title");
        Property updatedProperty = repository.save(savedProperty);

        assertEquals("Updated Title", updatedProperty.getTitle());
        assertTrue(updatedProperty.getUpdatedAt().isAfter(initialUpdatedAt));
    }
}
