package com.gpsolutions.hoteltask.api.controllers;


import com.gpsolutions.hoteltask.exceptions.InvalidFilterParameterException;
import com.gpsolutions.hoteltask.service.HotelStatisticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(HistogramController.class)
@ExtendWith(MockitoExtension.class)
class HistogramControllerTest {

    @MockitoBean
    private HotelStatisticsService hotelStatisticsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnHistogramData() throws Exception {
        Map<String, Long> histogramData = Map.of(
                "testKey1", 1L,
                "testKey2", 2L
        );

        when(hotelStatisticsService.getHistogram(anyString())).thenReturn(histogramData);

        mockMvc.perform(get("/property-view/histogram/brand")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testKey1").value(1L))
                .andExpect(jsonPath("$.testKey2").value(2L));
    }

    @Test
    void shouldReturnEmptyHistogramWhenNoData() throws Exception {
        when(hotelStatisticsService.getHistogram(anyString())).thenReturn(Map.of());

        mockMvc.perform(get("/property-view/histogram/brand")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturnErrorMessage() throws Exception {
        when(hotelStatisticsService.getHistogram(anyString()))
                .thenThrow(new InvalidFilterParameterException("testMessage"));
        mockMvc.perform(get("/property-view/histogram/brand")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect((jsonPath("$.length()").value(5)))
                .andExpect(jsonPath("$.title").value("InvalidFilterParameterException"))
                .andExpect(jsonPath("$.detail").value("testMessage"));
    }
}
