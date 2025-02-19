package com.gpsolutions.hoteltask.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArrivalTimeDto {
    @NotEmpty(message = "checkIn must not be null")
    private String checkIn;
    @NotEmpty(message = "checkOut must not be null")
    private String checkOut;
}
