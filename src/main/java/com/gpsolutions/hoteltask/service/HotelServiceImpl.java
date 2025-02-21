package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.api.dto.HotelCreateDtoRequest;
import com.gpsolutions.hoteltask.api.dto.HotelDetailsDtoResponse;
import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.entities.Amenity;
import com.gpsolutions.hoteltask.entities.Hotel;
import com.gpsolutions.hoteltask.exceptions.MethodNotCreatedException;
import com.gpsolutions.hoteltask.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository,
                            ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<HotelDtoResponse> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(el -> modelMapper.map(el, HotelDtoResponse.class))
                .toList();
    }

    @Override
    public HotelDetailsDtoResponse getHotelById(Long id) {
        return modelMapper.map(hotelRepository.findById(id), HotelDetailsDtoResponse.class);
    }

    @Override
    public void addAmenities(Long id, List<String> amenities) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> {
                    log.error("Hotel with id={} wasn't find", id);
                    return new RuntimeException();
                });
        hotelRepository.save(createHotelWithNewAmenities(amenities, hotel));
    }

    @Override
    public HotelDtoResponse createHotel(HotelCreateDtoRequest hotelCreateDtoRequest) {
        Hotel hotel = modelMapper.map(hotelCreateDtoRequest, Hotel.class);
        return modelMapper.map(hotelRepository.save(hotel), HotelDtoResponse.class);
    }

    @Override
    public Map<String, Long> getHistogram(String param) {
        throw new MethodNotCreatedException("Not available yet");
    }

    @Override
    public List<HotelDtoResponse> search(Map<String, String> params) {
        throw new MethodNotCreatedException("Not available yet");
    }

    private Hotel createHotelWithNewAmenities(List<String> amenities, Hotel hotel) {
        Set<String> currentAmenities =  hotel.getAmenities().stream()
                .map(Amenity::getName)
                .collect(Collectors.toSet());
        amenities.stream()
                .filter(str -> !currentAmenities.contains(str))
                .map(str -> modelMapper.map(str, Amenity.class))
                .forEach(amenity -> hotel.getAmenities().add(amenity));
        return hotel;
    }
}
