package com.gpsolutions.hoteltask.api.controllers;

import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.service.HotelSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view/search")
public class SearchController {
    private final HotelSearchService hotelSearchService;

    @Autowired
    public SearchController(HotelSearchService hotelSearchService) {
        this.hotelSearchService = hotelSearchService;
    }

    @GetMapping
    public List<HotelDtoResponse>  searchHotels(@RequestParam(required = false) Map<String, String> params,
                                                @RequestParam(required = false) List<String> amenities) {
        return hotelSearchService.search(params, amenities);
    }

}
