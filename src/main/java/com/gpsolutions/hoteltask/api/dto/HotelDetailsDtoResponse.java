package com.gpsolutions.hoteltask.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelDetailsDtoResponse {
    private Long id;
    private String name;
    private String brand;
    private AddressDto address;
    private ContactDto contacts;
    private ArrivalTimeDto arrivalTime;
    private List<String> amenities;
}
