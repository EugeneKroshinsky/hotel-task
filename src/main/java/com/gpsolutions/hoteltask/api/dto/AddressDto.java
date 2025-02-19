package com.gpsolutions.hoteltask.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;


}
