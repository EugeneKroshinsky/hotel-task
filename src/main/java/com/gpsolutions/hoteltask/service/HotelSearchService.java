package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;

import java.util.List;
import java.util.Map;

public interface HotelSearchService {
    List<HotelDtoResponse> search(Map<String, String> params, List<String> amenities);
}
