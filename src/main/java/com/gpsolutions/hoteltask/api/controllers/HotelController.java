package com.gpsolutions.hoteltask.api.controllers;

import com.gpsolutions.hoteltask.api.dto.HotelCreateDtoRequest;
import com.gpsolutions.hoteltask.api.dto.HotelDetailsDtoResponse;
import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property-view/hotels")
@Tag(name = "Hotel")
public class HotelController {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Operation(summary = "Find all hotels")
    @GetMapping
    public List<HotelDtoResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @Operation(summary = "Find a hotel by its id")
    @GetMapping("/{id}")
    public HotelDetailsDtoResponse getHotelById(@Parameter(description = "id of hotel to be searched")
                                                    @PathVariable Long id) {
        return hotelService.getHotelById(id);
    }


    @Operation(summary = "Create a new hotel")
    @PostMapping
    public ResponseEntity<HotelDtoResponse> createHotel(@RequestBody @Valid HotelCreateDtoRequest request) {
        HotelDtoResponse response = hotelService.createHotel(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Add amenities to hotel")
    @PostMapping("/{id}/amenities")
    public ResponseEntity<HttpStatus> addAmenities(@Parameter(description = "id of hotel to be updated")
                                                        @PathVariable Long id,
                                                   @Parameter(description = "amenities to be added")
                                                        @RequestBody List<String> amenities) {
         hotelService.addAmenities(id, amenities);
         return ResponseEntity.status(HttpStatus.OK).build();
    }
}
