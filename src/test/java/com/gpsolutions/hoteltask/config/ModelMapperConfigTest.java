package com.gpsolutions.hoteltask.config;

import static org.junit.jupiter.api.Assertions.*;

import com.gpsolutions.hoteltask.api.dto.HotelCreateDtoRequest;
import com.gpsolutions.hoteltask.api.dto.HotelDetailsDtoResponse;
import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.entities.Address;
import com.gpsolutions.hoteltask.entities.Amenity;
import com.gpsolutions.hoteltask.entities.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

class ModelMapperConfigTest {

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        ModelMapperConfig config = new ModelMapperConfig();
        modelMapper = config.modelMapper();
    }

    @Test
    void shouldMapStringToAmenity() {
        String amenityName = "test";

        Amenity amenity = modelMapper.map(amenityName, Amenity.class);

        assertNotNull(amenity);
        assertEquals(amenityName, amenity.getName());
    }

    @Test
    void shouldMapListStringToListAmenity() {
        List<String> amenities = List.of("test1", "test2");
        HotelCreateDtoRequest dtoRequest = new HotelCreateDtoRequest();
        dtoRequest.setAmenities(amenities);

        Hotel hotel = modelMapper.map(dtoRequest, Hotel.class);

        assertNotNull(hotel.getAmenities());
        assertEquals(2, hotel.getAmenities().size());
        assertTrue(
                hotel.getAmenities().stream()
                        .anyMatch(a -> a.getName().equals("test1"))
        );
        assertTrue(
                hotel.getAmenities().stream()
                        .anyMatch(a -> a.getName().equals("test2"))
        );
    }

    @Test
    void shouldMapAddressToString() {
        Address address = new Address();
        address.setHouseNumber("testNumber");
        address.setStreet("testStreet");
        address.setCity("testCity");
        address.setPostCode("testPostCode");
        address.setCountry("testCounty");

        Hotel hotel = new Hotel();
        hotel.setAddress(address);

        HotelDtoResponse response = modelMapper.map(hotel, HotelDtoResponse.class);

        assertNotNull(response.getAddress());
        assertEquals(
                "testNumber testStreet, testCity, testPostCode, testCounty",
                response.getAddress()
        );
    }

    @Test
    void shouldMapListAmenityToListString() {
        Amenity amenity1 = new Amenity();
        amenity1.setName("test1");
        Amenity amenity2 = new Amenity();
        amenity2.setName("test2");

        Hotel hotel = new Hotel();
        hotel.setAmenities(List.of(amenity1, amenity2));

        HotelDetailsDtoResponse response = modelMapper.map(hotel, HotelDetailsDtoResponse.class);

        assertNotNull(response.getAmenities());
        assertEquals(2, response.getAmenities().size());
        assertTrue(response.getAmenities().contains("test1"));
        assertTrue(response.getAmenities().contains("test2"));
    }
}
