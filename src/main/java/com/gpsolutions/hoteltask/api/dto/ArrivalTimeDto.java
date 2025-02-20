package com.gpsolutions.hoteltask.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArrivalTimeDto {
    @NotEmpty(message = "checkIn must not be null")
    private String checkIn;
    @NotEmpty(message = "checkOut must not be null")
    private String checkOut;
}
