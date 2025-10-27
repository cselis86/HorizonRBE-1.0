package org.elis.horizon.horizonrent.controller;

import org.elis.horizon.horizonrent.dto.PagedResponse;
import org.elis.horizon.horizonrent.dto.PropertyDetailResponse;
import org.elis.horizon.horizonrent.dto.PropertyListResponse;
import org.elis.horizon.horizonrent.repository.PropertyRepository;
import org.elis.horizon.horizonrent.model.PropertyStatus;
import org.elis.horizon.horizonrent.model.PropertyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Use a test profile if needed for specific configurations
class PropertyIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PropertyRepository propertyRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/properties";
    }

    @Test
    void testRepositoryHasData() {
        assertThat(propertyRepository.findAll()).isNotEmpty();
        assertThat(propertyRepository.count()).isGreaterThan(0);
    }

    @Test
    void getAllProperties_ShouldReturnPagedResponse() {
        // Given
        // The InMemoryPropertyRepository is pre-populated with sample data
        // When
        ResponseEntity<PagedResponse<PropertyListResponse>> response = restTemplate.exchange(
                baseUrl + "?page=0&size=10&sortBy=createdAt&sortOrder=desc",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PagedResponse<PropertyListResponse>>() {}
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).isNotEmpty();
        assertThat(response.getBody().getTotalElements()).isGreaterThan(0);
    }

    @Test
    void getPropertyById_WhenExists_ShouldReturnProperty() {
        // Given
        // Assuming there's at least one property in the InMemoryPropertyRepository
        Long existingPropertyId = 2L; // Based on sample data in InMemoryPropertyRepository

        // When
        ResponseEntity<PropertyDetailResponse> response = restTemplate.getForEntity(
                baseUrl + "/" + existingPropertyId,
                PropertyDetailResponse.class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(existingPropertyId);
        assertThat(response.getBody().getTitle()).isNotNull();
    }

    @Test
    void getPropertyById_WhenNotExists_ShouldReturn404() {
        // Given
        Long nonExistentPropertyId = 999L;

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(
                baseUrl + "/" + nonExistentPropertyId,
                String.class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getPropertyById_WhenInvalidId_ShouldReturn400() {
        // Given
        Long invalidPropertyId = 0L; // @Min(1) validation

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(
                baseUrl + "/" + invalidPropertyId,
                String.class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
