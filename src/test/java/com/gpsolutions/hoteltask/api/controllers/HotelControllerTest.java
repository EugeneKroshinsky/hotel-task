package com.gpsolutions.hoteltask.api.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.gpsolutions.hoteltask.api.dto.*;
import com.gpsolutions.hoteltask.exceptions.HotelCreationException;
import com.gpsolutions.hoteltask.exceptions.HotelNotFoundException;
import com.gpsolutions.hoteltask.service.HotelService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(HotelController.class)
@ExtendWith(MockitoExtension.class)
class HotelControllerTest {

    @MockitoBean
    private HotelService hotelService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static HotelCreateDtoRequest request;
    private static HotelDtoResponse response;
    private static HotelDetailsDtoResponse hotel;

    @BeforeAll
    static void setUp() {
        request = new HotelCreateDtoRequest(
                "testName",
                "testDescription",
                "testBrand",
                new AddressDto("testNumber", "testStreet", "testCity", "testCountry", "testPostcode"),
                new ContactDto("testPhone","testEmail@test"),
                new ArrivalTimeDto("testCheckIn", "testCheckOut"),
                List.of("testAmenity1", "testAmenity2")
        );
        response = new HotelDtoResponse(
                1L,
                "testName",
                "testDescription",
                "testAddress",
                "testContactsPhone"
        );

        hotel = new HotelDetailsDtoResponse(
                1L,
                "testName",
                "testDescription",
                new AddressDto("testNumber", "testStreet", "testCity", "testCountry", "testPostcode"),
                new ContactDto("testPhone","testEmail"),
                new ArrivalTimeDto("testCheckIn", "testCheckOut"),
                List.of("testAmenity1", "testAmenity2")
        );
    }

    @Test
    void shouldReturnAllHotels() throws Exception {
        List<HotelDtoResponse> hotels = List.of(response);
        when(hotelService.getAllHotels()).thenReturn(hotels);

        mockMvc.perform(get("/property-view/hotels")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("testName"));
    }

    @Test
    void shouldReturnHotelById() throws Exception {
        when(hotelService.getHotelById(anyLong())).thenReturn(hotel);

        mockMvc.perform(get("/property-view/hotels/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.amenities.length()").value(2));
    }

    @Test
    void shouldReturnNotFoundWhenHotelDoesNotExist() throws Exception {
        when(hotelService.getHotelById(anyLong())).thenThrow(new HotelNotFoundException(1L));

        mockMvc.perform(get("/property-view/hotels/999")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("HotelNotFoundException"))
                .andExpect(jsonPath("$.detail").value("Hotel with id 1 not found"));
    }

    @Test
    void shouldCreateHotel() throws Exception {

        when(hotelService.createHotel(any(HotelCreateDtoRequest.class))).thenReturn(response);

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("testName"));
    }
    @Test
    void shouldNotCreateHotelUnique() throws Exception {
        when(hotelService.createHotel(any(HotelCreateDtoRequest.class))).thenThrow(new HotelCreationException("testMessage", new RuntimeException()));
        mockMvc.perform(post("/property-view/hotels")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("HotelCreationException"));
    }
    @Test
    void shouldNotCreateHotelValidation() throws Exception {
        request.setName("");
        mockMvc.perform(post("/property-view/hotels")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.errors.name").value("Name must be not empty"));
        request.setName("testName");
    }
    @Test
    void shouldAddAmenitiesToHotel() throws Exception {
        mockMvc.perform(post("/property-view/hotels/1/amenities")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of("testAmenity1", "testAmenity2"))))
                .andExpect(status().isCreated());
    }
}
