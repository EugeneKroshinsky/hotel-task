package com.gpsolutions.hoteltask.api.controllers;

import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.service.HotelSearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view/search")
@Tag(name = "Search Hotel")
public class SearchController {
    private final HotelSearchService hotelSearchService;

    @Autowired
    public SearchController(HotelSearchService hotelSearchService) {
        this.hotelSearchService = hotelSearchService;
    }

    @Operation(summary = "Search hotels")
    @GetMapping
    public List<HotelDtoResponse>  searchHotels(@Parameter(description = "map of filters")
                                                    @RequestParam(required = false) Map<String, String> params,
                                                @Parameter(description = "amenities parameter")
                                                    @RequestParam(required = false) List<String> amenities) {
        return hotelSearchService.search(params, amenities);
    }

}
