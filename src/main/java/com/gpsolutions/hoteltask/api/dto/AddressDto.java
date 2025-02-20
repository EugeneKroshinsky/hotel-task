package com.gpsolutions.hoteltask.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDto {
    @NotEmpty(message = "houseNumber must not be null")
    private String houseNumber;
    @NotEmpty(message = "street must not be null")
    private String street;
    @NotEmpty(message = "city must not be null")
    private String city;
    @NotEmpty(message = "country must not be null")
    private String country;
    @NotEmpty(message = "postCode must not be null")
    private String postCode;


}
