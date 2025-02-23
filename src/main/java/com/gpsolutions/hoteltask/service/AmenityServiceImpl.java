package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.entities.Amenity;
import com.gpsolutions.hoteltask.entities.Hotel;
import com.gpsolutions.hoteltask.repository.AmenityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AmenityServiceImpl implements AmenityService{
    private final AmenityRepository amenityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AmenityServiceImpl(AmenityRepository amenityRepository, ModelMapper modelMapper) {
        this.amenityRepository = amenityRepository;
        this.modelMapper = modelMapper;
    }

    public Hotel addAmenitiesToHotel(Hotel hotel, List<String> amenities) {
        Set<String> currentAmenities = hotel.getAmenities()
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

    public Amenity findOrCreateAmenity(Amenity amenity) {
        return amenityRepository.findByName(amenity.getName())
                .orElseGet(() -> amenityRepository.save(amenity));
    }
}