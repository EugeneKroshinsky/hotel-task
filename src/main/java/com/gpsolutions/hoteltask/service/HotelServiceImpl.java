package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.api.dto.HotelCreateDtoRequest;
import com.gpsolutions.hoteltask.api.dto.HotelDetailsDtoResponse;
import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.entities.Amenity;
import com.gpsolutions.hoteltask.entities.Hotel;
import com.gpsolutions.hoteltask.exceptions.HotelCreationException;
import com.gpsolutions.hoteltask.exceptions.HotelNotFoundException;
import com.gpsolutions.hoteltask.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final AmenityService amenityService;
    private final ModelMapper modelMapper;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository,
                            AmenityService amenityService,
                            ModelMapper modelMapper
                            ) {
        this.hotelRepository = hotelRepository;
        this.amenityService = amenityService;
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
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> {
                log.error("Hotel with id {} not found", id);
                return new HotelNotFoundException(id);
            });
        return modelMapper.map(hotel, HotelDetailsDtoResponse.class);
    }

    @Transactional
    @Override
    public HotelDtoResponse createHotel(HotelCreateDtoRequest hotelCreateDtoRequest) {
        Hotel hotel = modelMapper.map(hotelCreateDtoRequest, Hotel.class);
        try {
            List<Amenity> processedAmenities = hotel.getAmenities().stream()
                    .map(amenityService::findOrCreateAmenity)
                    .toList();
            hotel.setAmenities(processedAmenities);
            Hotel createdHotel = hotelRepository.save(hotel);
            return modelMapper.map(createdHotel, HotelDtoResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Hotel constraints exception");
            throw new HotelCreationException("Hotel constraints exception", e);
        }
    }

    @Transactional
    @Override
    public void addAmenities(Long id, List<String> amenities) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> {
                log.error("Hotel with id {} not found", id);
                return new HotelNotFoundException(id);
            });
        Hotel hotelWithNewAmenities = amenityService.addAmenitiesToHotel(hotel, amenities);
        hotelRepository.save(hotelWithNewAmenities);
    }

}
