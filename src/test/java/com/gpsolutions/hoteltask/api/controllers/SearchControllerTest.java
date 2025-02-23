package com.gpsolutions.hoteltask.api.controllers;

import com.gpsolutions.hoteltask.api.controllers.SearchController;
import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.service.HotelSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(SearchController.class)
@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    @MockitoBean
    private HotelSearchService hotelSearchService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldReturnHotelsWhenSearched() throws Exception {

        HotelDtoResponse hotel1 = new HotelDtoResponse(
                1L,
                "testName",
                "testBrand",
                "testAddress",
                "testPhone"
        );

        when(hotelSearchService.search(anyMap(), anyList())).thenReturn(List.of(hotel1));

        mockMvc.perform(get("/property-view/search")
                        .param("testKey", "testValue")
                        .param("amenities", "testAmenity")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("testName"));
    }

    @Test
    void shouldReturnEmptyListWhenNoResults() throws Exception {
        when(hotelSearchService.search(anyMap(), anyList())).thenReturn(List.of());

        mockMvc.perform(get("/property-view/search")
                        .param("testKey", "testValue")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
