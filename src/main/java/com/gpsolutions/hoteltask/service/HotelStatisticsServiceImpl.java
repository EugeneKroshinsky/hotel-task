package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HotelStatisticsServiceImpl implements HotelStatisticsService{
    private final HotelRepository hotelRepository;

    @Autowired
    public HotelStatisticsServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
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

    private Map<String, Long> createMap(List<Object[]> result) {
        return result.stream()
                .collect(Collectors.toMap(
                        row -> row[0] != null ? row[0].toString() : "Unknown",
                        row -> row[1] instanceof Number ? ((Number) row[1]).longValue() : 0L
                ));
    }
}
