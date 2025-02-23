package com.gpsolutions.hoteltask.api.controllers;

import com.gpsolutions.hoteltask.service.HotelStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/property-view/histogram")
public class HistogramController {
    private final HotelStatisticsService hotelStatisticsService;

    @Autowired
    public HistogramController(HotelStatisticsService hotelStatisticsService) {
        this.hotelStatisticsService = hotelStatisticsService;
    }

    @GetMapping("/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelStatisticsService.getHistogram(param);
    }
}
