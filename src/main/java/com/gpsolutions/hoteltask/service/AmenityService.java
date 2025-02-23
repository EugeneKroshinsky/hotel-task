package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.entities.Amenity;
import com.gpsolutions.hoteltask.entities.Hotel;

import java.util.List;

public interface AmenityService {
    Hotel addAmenitiesToHotel(Hotel hotel, List<String> amenities);
    Amenity findOrCreateAmenity(Amenity amenity);
}
