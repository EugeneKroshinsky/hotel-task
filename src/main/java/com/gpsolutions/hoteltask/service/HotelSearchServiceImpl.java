package com.gpsolutions.hoteltask.service;

import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
import com.gpsolutions.hoteltask.entities.Hotel;
import com.gpsolutions.hoteltask.repository.HotelRepository;
import com.gpsolutions.hoteltask.specification.HotelSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HotelSearchServiceImpl implements HotelSearchService{
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HotelSearchServiceImpl(HotelRepository hotelRepository,
                                  ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<HotelDtoResponse> search(Map<String, String> params, List<String> amenities) {
        Specification<Hotel> specification = HotelSpecification.filterByParams(params, amenities);
        return hotelRepository.findAll(specification).stream()
                .map(hotel -> modelMapper.map(hotel, HotelDtoResponse.class))
                .toList();
    }
}
