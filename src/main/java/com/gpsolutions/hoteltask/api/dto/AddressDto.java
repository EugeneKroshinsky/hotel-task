package com.gpsolutions.hoteltask.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDto {
    @NotBlank(message = "houseNumber must not be null")
    private String houseNumber;
    @NotBlank(message = "street must not be null")
    private String street;
    @NotBlank(message = "city must not be null")
    private String city;
    @NotBlank(message = "country must not be null")
    private String country;
    @NotBlank(message = "postCode must not be null")
    private String postCode;
}
