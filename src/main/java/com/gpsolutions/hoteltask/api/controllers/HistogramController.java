package com.gpsolutions.hoteltask.api.controllers;

import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.service.HotelStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/property-view/histogram")
@Tag(name = "Histogram Hotel")
public class HistogramController {
    private final HotelStatisticsService hotelStatisticsService;

    @Autowired
    public HistogramController(HotelStatisticsService hotelStatisticsService) {
        this.hotelStatisticsService = hotelStatisticsService;
    }

    @Operation(summary = "Get a hotel histogram")
    @GetMapping("/{param}")
    public Map<String, Long> getHistogram(@Parameter(description = "Histogram parameter")
                                              @PathVariable String param) {
        return hotelStatisticsService.getHistogram(param);
    }
}
