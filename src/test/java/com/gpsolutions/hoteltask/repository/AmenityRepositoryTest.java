package com.gpsolutions.hoteltask.repository;

import com.gpsolutions.hoteltask.entities.Amenity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class AmenityRepositoryTest {

    @Autowired
    private AmenityRepository amenityRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Amenity amenity;

    @BeforeEach
    void setUp() {
        amenity = new Amenity();
        amenity.setName("testAmenity");
    }

    @Test
    void shouldFindAmenityById() {
        entityManager.persist(amenity);
        entityManager.flush();

        Optional<Amenity> found = amenityRepository.findById(amenity.getId());

        assertTrue(found.isPresent());
        assertEquals("testAmenity", found.get().getName());
    }

    @Test
    void shouldFindAmenityByName() {
        entityManager.persist(amenity);
        entityManager.flush();

        Optional<Amenity> found = amenityRepository.findByName("testAmenity");

        assertTrue(found.isPresent());
        assertEquals("testAmenity", found.get().getName());
    }
}
