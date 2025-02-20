package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.api.dto.HotelCreateDtoRequest;
import com.gpsolutions.hoteltask.api.dto.HotelDetailsDtoResponse;
import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.entities.Hotel;
import com.gpsolutions.hoteltask.exceptions.MethodNotCreatedException;
import com.gpsolutions.hoteltask.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Slf4j
@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HotelService(HotelRepository hotelRepository,
                        ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    public List<HotelDtoResponse> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(el -> modelMapper.map(el, HotelDtoResponse.class))
                .toList();
    }

    public HotelDetailsDtoResponse getHotelById(Long id) {
        return modelMapper.map(hotelRepository.findById(id), HotelDetailsDtoResponse.class);
    }

    public void addAmenities(Long id, List<String> amenities) {
        throw new MethodNotCreatedException("Not available yet");
    }

    public HotelDtoResponse createHotel(HotelCreateDtoRequest hotelCreateDtoRequest) {
        log.info("HotelCreateDtoRequest " + hotelCreateDtoRequest);
        Hotel hotel = modelMapper.map(hotelCreateDtoRequest, Hotel.class);
        log.info("Hotel" +hotel);
        Hotel saveHotel = hotelRepository.save(hotel);
        log.info("SaveHotel (Hotel)" + saveHotel);
        return modelMapper.map(saveHotel, HotelDtoResponse.class);
    }

    public Map<String, Long> getHistogram(String param) {
        throw new MethodNotCreatedException("Not available yet");
    }

    public List<HotelDtoResponse> search(Map<String, String> params) {
        throw new MethodNotCreatedException("Not available yet");
    }
}
