package com.gpsolutions.hoteltask.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HotelCreateDtoRequest {
    @NotEmpty(message = "Name must be not empty")
    private String name;

    private String description;

    private String brand;

    @NotNull(message = "address dto must be not null")
    private AddressDto address;

    @NotNull(message = "address dto must be not null")
    private ContactDto contacts;

    @NotNull(message = "arrivalTime dto must be not null")
    private ArrivalTimeDto arrivalTime;
}
