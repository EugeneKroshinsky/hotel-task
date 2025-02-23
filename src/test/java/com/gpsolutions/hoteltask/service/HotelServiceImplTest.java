package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.entities.Hotel;
import com.gpsolutions.hoteltask.repository.AmenityRepository;
import com.gpsolutions.hoteltask.repository.HotelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {
    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private static Hotel hotel;
    private static HotelDtoResponse hotelDtoResponse;

    @BeforeAll
    public static void beforeAll() {
        hotel = new Hotel();
        hotel.setName("test");
        hotelDtoResponse = new HotelDtoResponse();
        hotelDtoResponse.setName("test");
    }

    @Test
    public void getAllHotelsTest() {
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));
        when(modelMapper.map(hotel, HotelDtoResponse.class)).thenReturn(hotelDtoResponse);

        List<HotelDtoResponse> result = hotelService.getAllHotels();

        assertEquals(1, result.size());
        assertEquals("test", result.get(0).getName());
    }
    @Test
    public void getAllHotelsWrongTest() {

    }
}
