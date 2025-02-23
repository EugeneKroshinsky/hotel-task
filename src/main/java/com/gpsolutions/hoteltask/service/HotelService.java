package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.api.dto.HotelCreateDtoRequest;
import com.gpsolutions.hoteltask.api.dto.HotelDetailsDtoResponse;
import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;

import java.util.List;
import java.util.Map;

public interface HotelService {
    List<HotelDtoResponse> getAllHotels();
    HotelDetailsDtoResponse getHotelById(Long id);
    HotelDtoResponse createHotel(HotelCreateDtoRequest hotelCreateDtoRequest);
    void addAmenities(Long id, List<String> amenities);

}
