package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.api.dto.HotelCreateDtoRequest;
import com.gpsolutions.hoteltask.api.dto.HotelDetailsDtoResponse;
import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.entities.Amenity;
import com.gpsolutions.hoteltask.entities.Hotel;
import com.gpsolutions.hoteltask.exceptions.HotelCreationException;
import com.gpsolutions.hoteltask.exceptions.HotelNotFoundException;
import com.gpsolutions.hoteltask.repository.AmenityRepository;
import com.gpsolutions.hoteltask.repository.HotelRepository;
import com.gpsolutions.hoteltask.specification.HotelSpecification;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository,
                            AmenityRepository amenityRepository,
                            ModelMapper modelMapper
                            ) {
        this.hotelRepository = hotelRepository;
        this.amenityRepository = amenityRepository;
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
                    .map(this::findOrCreateAmenity)
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
        hotelRepository.save(createHotelWithNewAmenities(amenities, hotel));
    }

    @Override
    public List<HotelDtoResponse> search(Map<String, String> params, List<String> amenities) {
        Specification<Hotel> specification = HotelSpecification.filterByParams(params, amenities);
        return hotelRepository.findAll(specification).stream()
                .map(hotel -> modelMapper.map(hotel, HotelDtoResponse.class))
                .toList();
    }

    @Override
    public Map<String, Long> getHistogram(String param) {
        return  switch (param) {
            case "city" -> createMap(hotelRepository.countHotelsByCity());
            case "country" -> createMap(hotelRepository.countHotelsByCountry());
            case "brand" -> createMap(hotelRepository.countHotelsByBrand());
            case "amenities" -> createMap(hotelRepository.countHotelByAmenity());
            default -> {
                log.error("Param \"" + param + "\" isn't match");
                throw new IllegalArgumentException("Param \"" + param + "\" isn't match");
            }
        };
    }


    private Hotel createHotelWithNewAmenities(List<String> amenities, Hotel hotel) {
        Set<String> currentAmenities =  hotel.getAmenities()
                .stream()
                .map(Amenity::getName)
                .collect(Collectors.toSet());
        amenities.stream()
                .filter(currentAmenities::add)
                .map(str -> modelMapper.map(str, Amenity.class))
                .map(this::findOrCreateAmenity)
                .forEach(amenity -> hotel.getAmenities().add(amenity));
        return hotel;
    }


    private Map<String, Long> createMap(List<Object[]> result) {
        return result.stream()
                .collect(Collectors.toMap(
                        row -> row[0] != null ? row[0].toString() : "Unknown",
                        row -> row[1] instanceof Number ? ((Number) row[1]).longValue() : 0L
                ));
    }

    private Amenity findOrCreateAmenity(Amenity amenity) {
        return amenityRepository.findByName(amenity.getName())
                .orElseGet(() -> amenityRepository.save(amenity));
    }
}
