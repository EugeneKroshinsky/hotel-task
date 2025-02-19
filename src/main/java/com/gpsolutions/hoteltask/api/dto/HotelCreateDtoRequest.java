package com.gpsolutions.hoteltask.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelCreateDtoRequest {
    private String name;
    private String description;
    private String brand;
    private AddressDto address;
    private ContactDto contacts;
    private ArrivalTimeDto arrivalTime;
}
