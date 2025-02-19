package com.gpsolutions.hoteltask.api.controllers;

import com.gpsolutions.hoteltask.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
