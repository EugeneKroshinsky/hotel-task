package com.gpsolutions.hoteltask.api.controllers;

import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.exceptions.InvalidFilterParameterException;
import com.gpsolutions.hoteltask.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view/search")
public class SearchController {
    private final HotelService hotelService;

    @Autowired
    public SearchController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<HotelDtoResponse>  searchHotels(@RequestParam(required = false) Map<String, String> params,
                                                @RequestParam(required = false) List<String> amenities) {
        return hotelService.search(params, amenities);
    }

}
