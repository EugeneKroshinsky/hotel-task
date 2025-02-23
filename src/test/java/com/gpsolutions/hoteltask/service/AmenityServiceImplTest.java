package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.entities.Amenity;
import com.gpsolutions.hoteltask.entities.Hotel;
import com.gpsolutions.hoteltask.repository.AmenityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmenityServiceImplTest {

    @Mock
    private AmenityRepository amenityRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AmenityServiceImpl amenityService;

    private static Hotel hotel;
    private static Amenity amenity1;
    private static Amenity amenity2;
    private static List<String> stringAmenities;
    private static List<Amenity> amenities;

    @BeforeAll
    static void  setUp() {
        hotel = new Hotel();
        amenity1 = new Amenity();
        amenity1.setName("test1");
        amenity2 = new Amenity();
        amenity2.setName("test2");
    }

    @BeforeEach
    void setUpEach() {
        stringAmenities = new ArrayList<>();
        amenities = new ArrayList<>();
    }

    @Test
    void addAmenitiesToHotelShouldAddNewAmenity() {
        stringAmenities.add("test1");
        stringAmenities.add("test2");
        amenities.add(amenity1);
        hotel.setAmenities(amenities);

        when(modelMapper.map("test2", Amenity.class)).thenReturn(amenity2);
        when(amenityRepository.findByName("test2")).thenReturn(Optional.empty());
        when(amenityRepository.save(amenity2)).thenReturn(amenity2);

        Hotel updatedHotel = amenityService.addAmenitiesToHotel(hotel, stringAmenities);
        assertEquals(2, updatedHotel.getAmenities().size());
        assertTrue(
                updatedHotel.getAmenities().stream()
                        .anyMatch(el -> el.getName().equals("test1"))
        );
        assertTrue(
                updatedHotel.getAmenities().stream()
                        .anyMatch(el -> el.getName().equals("test2"))
        );
    }

    @Test
    void addAmenitiesToHotelShouldNullAmenities() {
        hotel.setAmenities(amenities);
        stringAmenities.add("test1");
        when(modelMapper.map("test1", Amenity.class)).thenReturn(amenity1);
        when(amenityRepository.findByName("test1")).thenReturn(Optional.empty());
        when(amenityRepository.save(amenity1)).thenReturn(amenity1);

        Hotel updatedHotel = amenityService.addAmenitiesToHotel(hotel, stringAmenities);
        assertEquals(1, updatedHotel.getAmenities().size());
        assertTrue(
                updatedHotel.getAmenities().stream()
                        .anyMatch(el -> el.getName().equals("test1"))
        );
    }

    @Test
    void addAmenitiesToHotelNullList() {
        amenities.add(amenity1);
        hotel.setAmenities(amenities);
        Hotel updatedHotel = amenityService.addAmenitiesToHotel(hotel, stringAmenities);
        assertEquals(1, updatedHotel.getAmenities().size());
        assertTrue(
                updatedHotel.getAmenities().stream()
                        .anyMatch(el -> el.getName().equals("test1"))
        );
    }


    @Test
    void findOrCreateAmenityShouldReturnExistingAmenity() {
        when(amenityRepository.findByName(anyString())).thenReturn(Optional.of(amenity1));
        Amenity result = amenityService.findOrCreateAmenity(amenity1);
        assertEquals(amenity1, result);
    }

    @Test
    void findOrCreateAmenity_ShouldCreateNewAmenity() {
        when(amenityRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(amenityRepository.save(amenity1)).thenReturn(amenity2);
        Amenity result = amenityService.findOrCreateAmenity(amenity1);
        assertEquals(amenity2, result);
    }
}
