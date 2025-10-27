package org.elis.horizon.horizonrent.controller;

import org.elis.horizon.horizonrent.dto.PagedResponse;
import org.elis.horizon.horizonrent.dto.PropertyDetailResponse;
import org.elis.horizon.horizonrent.dto.PropertyListResponse;
import org.elis.horizon.horizonrent.exception.PropertyNotFoundException;
import org.elis.horizon.horizonrent.model.Address;
import org.elis.horizon.horizonrent.model.Amenity;
import org.elis.horizon.horizonrent.model.Image;
import org.elis.horizon.horizonrent.model.PropertyStatus;
import org.elis.horizon.horizonrent.model.PropertyType;
import org.elis.horizon.horizonrent.service.PropertyService;
import org.elis.horizon.horizonrent.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PropertyController.class, excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropertyController controller;

    @MockBean
    private PropertyService propertyService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserDetailsService userDetailsService;

    private PropertyListResponse sampleProperty;
    private PropertyDetailResponse sampleDetailProperty;

    @BeforeEach
    void setUp() {
        sampleProperty = createSampleListResponse();
        sampleDetailProperty = createSampleDetailResponse();
    }

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void getAllProperties_ShouldReturnPagedResponse() throws Exception {
        // Arrange
        List<PropertyListResponse> properties = Arrays.asList(sampleProperty);
        PagedResponse<PropertyListResponse> pagedResponse =
                PagedResponse.of(properties, 1, 0, 10);

        when(propertyService.getAllProperties(0, 10, "createdAt", "desc"))
                .thenReturn(pagedResponse);

        // Act & Assert
        mockMvc.perform(get("/api/properties")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "createdAt")
                        .param("sortOrder", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Modern Apartment"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(jsonPath("$.pageSize").value(10));
    }

    @Test
    void getAllProperties_WithInvalidSortField_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/properties")
                        .param("sortBy", "invalidField"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPropertyById_WhenExists_ShouldReturnProperty() throws Exception {
        // Arrange
        when(propertyService.getPropertyById(1L))
                .thenReturn(sampleDetailProperty);

        // Act & Assert
        mockMvc.perform(get("/api/properties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Modern Apartment"))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.images", hasSize(greaterThan(0))));
    }

    @Test
    void getPropertyById_WhenNotExists_ShouldReturn404() throws Exception {
        // Arrange
        when(propertyService.getPropertyById(anyLong()))
                .thenThrow(new PropertyNotFoundException("Property not found"));

        // Act & Assert
        mockMvc.perform(get("/api/properties/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPropertyById_WhenInvalidId_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/properties/0")) // ID must be positive
                .andExpect(status().isBadRequest());
    }

    private PropertyListResponse createSampleListResponse() {
        PropertyListResponse property = new PropertyListResponse();
        property.setId(1L);
        property.setTitle("Modern Apartment");
        property.setCity("Austin");
        property.setState("TX");
        property.setPrice(BigDecimal.valueOf(2500));
        property.setBedrooms(2);
        property.setBathrooms(2);
        property.setSquareFeet(1200);
        property.setPropertyType(PropertyType.APARTMENT);
        property.setPrimaryImageUrl("https://example.com/image.jpg");
        property.setStatus(PropertyStatus.AVAILABLE);
        return property;
    }

    private PropertyDetailResponse createSampleDetailResponse() {
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
}