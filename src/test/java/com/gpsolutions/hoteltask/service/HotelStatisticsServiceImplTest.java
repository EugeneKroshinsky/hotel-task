package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.repository.HotelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
class HotelStatisticsServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelStatisticsServiceImpl hotelStatisticsService;

    private static List<Object[]> mockData;

    @BeforeAll
    static void setUp() {
        mockData = List.of(new Object[]{"test1", 1L}, new Object[]{"test2", 2L});
    }

    @Test
    void getHistogram_ShouldReturnCityHistogram() {
        when(hotelRepository.countHotelsByCity()).thenReturn(mockData);

        Map<String, Long> result = hotelStatisticsService.getHistogram("city");

        assertEquals(2, result.size());
        assertEquals(1L, result.get("test1"));
        assertEquals(2L, result.get("test2"));
    }

    @Test
    void getHistogram_ShouldReturnCountryHistogram() {
        when(hotelRepository.countHotelsByCountry()).thenReturn(mockData);

        Map<String, Long> result = hotelStatisticsService.getHistogram("country");

        assertEquals(2, result.size());
        assertEquals(1L, result.get("test1"));
        assertEquals(2L, result.get("test2"));
    }

    @Test
    void getHistogram_ShouldReturnBrandHistogram() {
        when(hotelRepository.countHotelsByBrand()).thenReturn(mockData);

        Map<String, Long> result = hotelStatisticsService.getHistogram("brand");

        assertEquals(2, result.size());
        assertEquals(1L, result.get("test1"));
        assertEquals(2L, result.get("test2"));
    }

    @Test
    void getHistogram_ShouldReturnAmenitiesHistogram() {
        when(hotelRepository.countHotelByAmenity()).thenReturn(mockData);

        Map<String, Long> result = hotelStatisticsService.getHistogram("amenities");

        assertEquals(2, result.size());
        assertEquals(1L, result.get("test1"));
        assertEquals(2L, result.get("test2"));
    }

    @Test
    void getHistogram_ShouldThrowExceptionForInvalidParam() {
        assertThrows(
                IllegalArgumentException.class,
                () -> hotelStatisticsService.getHistogram("illegalParam")
        );
    }
}
