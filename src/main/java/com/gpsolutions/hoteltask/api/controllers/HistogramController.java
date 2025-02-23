package com.gpsolutions.hoteltask.api.controllers;

import com.gpsolutions.hoteltask.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/property-view/histogram")
public class HistogramController {
    private final HotelService hotelService;

    @Autowired
    public HistogramController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelService.getHistogram(param);
    }
}
