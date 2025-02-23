package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.api.dto.HotelCreateDtoRequest;
import com.gpsolutions.hoteltask.api.dto.HotelDetailsDtoResponse;
import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.entities.Amenity;
import com.gpsolutions.hoteltask.entities.Hotel;
import com.gpsolutions.hoteltask.exceptions.HotelCreationException;
import com.gpsolutions.hoteltask.exceptions.HotelNotFoundException;
import com.gpsolutions.hoteltask.repository.HotelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {
    @Mock
    private AmenityService amenityService;
    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private static Hotel hotel;
    private static Long id;
    private static Amenity amenity;
    private static HotelDtoResponse hotelDtoResponse;
    private static HotelCreateDtoRequest hotelCreateDtoRequest;
    private static HotelDetailsDtoResponse hotelDetailsDtoResponse;

    @BeforeAll
    public static void beforeAll() {
        hotel = new Hotel();
        hotel.setName("test");
        id = 1L;
        amenity = new Amenity();
        amenity.setName("testAmenity");
        hotelDtoResponse = new HotelDtoResponse();
        hotelDtoResponse.setName("test");
        hotelDetailsDtoResponse = new HotelDetailsDtoResponse();
        hotelDetailsDtoResponse.setName("test");
    }

    @Test
    void getAllHotelsTest() {
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));
        when(modelMapper.map(hotel, HotelDtoResponse.class)).thenReturn(hotelDtoResponse);
        List<HotelDtoResponse> result = hotelService.getAllHotels();
        assertEquals(1, result.size());
        assertEquals("test", result.get(0).getName());
    }
    @Test
    void getHotelByIdTest() {
        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));
        when(modelMapper.map(hotel, HotelDetailsDtoResponse.class)).thenReturn(hotelDetailsDtoResponse);
        HotelDetailsDtoResponse result = hotelService.getHotelById(id);
        assertEquals("test", result.getName());
    }

    @Test
    void getHotelByIdNotFoundTest() {
        when(hotelRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(
                HotelNotFoundException.class,
                () -> hotelService.getHotelById(id)
        );
    }
    @Test
    void addAmenitiesHotelNotFoundTest() {
        when(hotelRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(
                HotelNotFoundException.class,
                () -> hotelService.addAmenities(id, List.of("test1", "test2"))
        );
    }

    @Test
    void createHotelSuccess() {
        hotel.setAmenities(List.of(amenity));
        HotelDtoResponse response = new HotelDtoResponse();
        when(modelMapper.map(hotelCreateDtoRequest, Hotel.class)).thenReturn(hotel);
        when(amenityService.findOrCreateAmenity(amenity)).thenReturn(amenity);
        when(hotelRepository.save(hotel)).thenReturn(hotel);
        when(modelMapper.map(hotel, HotelDtoResponse.class)).thenReturn(response);
        HotelDtoResponse result = hotelService.createHotel(hotelCreateDtoRequest);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void createHotel_ThrowsHotelCreationException() {
        hotel.setAmenities(List.of(amenity));
        when(modelMapper.map(hotelCreateDtoRequest, Hotel.class)).thenReturn(hotel);
        when(amenityService.findOrCreateAmenity(amenity)).thenReturn(amenity);
        when(hotelRepository.save(hotel)).thenThrow(new DataIntegrityViolationException("test"));
        assertThrows(
                HotelCreationException.class,
                () -> hotelService.createHotel(hotelCreateDtoRequest)
        );
    }
}
