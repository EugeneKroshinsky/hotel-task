package com.gpsolutions.hoteltask.repository;

import com.gpsolutions.hoteltask.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Hotel hotel;
    private Address address;
    private Amenity amenity;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        hotel.setName("testName");
        hotel.setBrand("testBrand");

        address = new Address();
        address.setCity("testCity");
        address.setHouseNumber("testHouseNumber");
        address.setStreet("testStreet");
        address.setCountry("testCountry");
        address.setPostCode("testPostCode");
        hotel.setAddress(address);

        Contact contact = new Contact();
        contact.setPhone("testPhone");
        contact.setEmail("testEmail@test");
        hotel.setContacts(contact);

        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn("testCheckIn");
        arrivalTime.setCheckOut("testCheckOut");
        hotel.setArrivalTime(arrivalTime);

        amenity = new Amenity();
        amenity.setName("testAmenity");

        hotel.setAmenities(List.of(amenity));
    }


    @Test
    void shouldSaveAndFindHotel() {
        entityManager.persist(hotel);
        entityManager.flush();

        Optional<Hotel> found = hotelRepository.findById(hotel.getId());

        assertTrue(found.isPresent());
        assertEquals("testName", found.get().getName());
    }

    @Test
    void shouldFindAllHotels() {
        entityManager.persist(hotel);
        entityManager.flush();

        List<Hotel> hotels = hotelRepository.findAll();

        assertEquals(1, hotels.size());
    }

    @Test
    void shouldCountHotelsByCity() {
        entityManager.persist(address);
        hotel.setAddress(address);
        entityManager.persist(hotel);
        entityManager.flush();

        List<Object[]> result = hotelRepository.countHotelsByCity();

        assertFalse(result.isEmpty());
        assertEquals("testCity", result.get(0)[0]);
        assertEquals(1L, result.get(0)[1]);
    }

    @Test
    void shouldCountHotelsByCountry() {
        entityManager.persist(address);
        hotel.setAddress(address);
        entityManager.persist(hotel);
        entityManager.flush();

        List<Object[]> result = hotelRepository.countHotelsByCountry();

        assertFalse(result.isEmpty());
        assertEquals("testCountry", result.get(0)[0]);
        assertEquals(1L, result.get(0)[1]);
    }

    @Test
    void shouldCountHotelsByBrand() {
        entityManager.persist(hotel);
        entityManager.flush();

        List<Object[]> result = hotelRepository.countHotelsByBrand();

        assertFalse(result.isEmpty());
        assertEquals("testBrand", result.get(0)[0]);
        assertEquals(1L, result.get(0)[1]);
    }

    @Test
    void shouldCountHotelsByAmenity() {
        entityManager.persist(amenity);
        List<Amenity> amenities = new ArrayList<>();
        amenities.add(amenity);
        hotel.setAmenities(amenities);
        entityManager.persist(hotel);
        entityManager.flush();

        List<Object[]> result = hotelRepository.countHotelByAmenity();

        assertFalse(result.isEmpty());
        assertEquals("testAmenity", result.get(0)[0]);
        assertEquals(1L, result.get(0)[1]);
    }
}
