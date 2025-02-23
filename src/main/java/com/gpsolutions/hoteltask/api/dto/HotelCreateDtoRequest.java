package com.gpsolutions.hoteltask.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HotelCreateDtoRequest {
    @NotBlank(message = "Name must be not empty")
    private String name;

    @NotBlank(message = "Description must be not empty")
    private String description;

    @NotBlank(message = "Brand must be not empty")
    private String brand;

    @Valid
    @NotNull(message = "address dto must be not null")
    private AddressDto address;

    @Valid
    @NotNull(message = "address dto must be not null")
    private ContactDto contacts;

    @Valid
    @NotNull(message = "arrivalTime dto must be not null")
    private ArrivalTimeDto arrivalTime;

    private List<String> amenities;
}
