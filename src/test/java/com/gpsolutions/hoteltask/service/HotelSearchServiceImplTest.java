package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.entities.Hotel;
import com.gpsolutions.hoteltask.repository.HotelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class HotelSearchServiceImplTest {
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private HotelSearchServiceImpl hotelSearchService;

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
    public void searchTest() {
        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotel));
        when(modelMapper.map(hotel, HotelDtoResponse.class)).thenReturn(hotelDtoResponse);
        List<HotelDtoResponse> result = hotelSearchService.search(null, null);
        assertEquals(1,result.size());
        assertEquals(hotelDtoResponse, result.get(0));
    }

}
