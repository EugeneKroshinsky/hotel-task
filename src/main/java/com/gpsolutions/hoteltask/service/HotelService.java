package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.api.dto.HotelCreateDtoRequest;
import com.gpsolutions.hoteltask.api.dto.HotelDetailsDtoResponse;
import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.exceptions.MethodNotCreatedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HotelService {
    public List<HotelDtoResponse> getAllHotels() {
        throw new MethodNotCreatedException("Not supported yet");
    }

    public HotelDetailsDtoResponse getHotelById(Long id) {
        throw new MethodNotCreatedException("Not supported yet");
    }

    public void addAmenities(Long id, List<String> amenities) {
        throw new MethodNotCreatedException("Not supported yet");
    }

    public HotelDtoResponse createHotel(HotelCreateDtoRequest hotelCreateDtoRequest) {
        throw new MethodNotCreatedException("Not supported yet");
    }

    public Map<String, Long> getHistogram(String param) {
        throw new MethodNotCreatedException("Not supported yet");
    }

    public List<HotelDtoResponse> search(Map<String, String> params) {
        throw new MethodNotCreatedException("Not supported yet");
    }
}
