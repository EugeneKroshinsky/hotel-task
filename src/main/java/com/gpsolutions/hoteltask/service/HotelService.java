package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.api.dto.HotelCreateDtoRequest;
import com.gpsolutions.hoteltask.api.dto.HotelDetailsDtoResponse;
import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    public List<HotelDtoResponse> getAllHotels() {
        return null;
    }

    public HotelDetailsDtoResponse getHotelById(Long id) {
        return null;
    }

    public void addAmenities(Long id, List<String> amenities) {

    }

    public HotelDtoResponse createHotel(HotelCreateDtoRequest hotelCreateDtoRequest) {
        return null;
    }
}
