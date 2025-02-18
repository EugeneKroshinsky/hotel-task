package com.gpsolutions.hoteltask.api.controllers;

import com.gpsolutions.hoteltask.api.dto.HotelCreateDtoRequest;
import com.gpsolutions.hoteltask.api.dto.HotelDetailsDtoResponse;
import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property-view/hotels")
public class HotelController {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<HotelDtoResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public HotelDetailsDtoResponse getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping
    public ResponseEntity<HotelDtoResponse> createHotel(@RequestBody HotelCreateDtoRequest request) {
        HotelDtoResponse response = hotelService.createHotel(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/amenities")
    public ResponseEntity<HttpStatus> addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        hotelService.addAmenities(id, amenities);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
