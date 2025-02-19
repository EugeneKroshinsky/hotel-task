package com.gpsolutions.hoteltask.api.controllers;

import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<HotelDtoResponse>  searchHotels(@RequestParam Map<String, String> params) {
        return hotelService.search(params);
    }

}
