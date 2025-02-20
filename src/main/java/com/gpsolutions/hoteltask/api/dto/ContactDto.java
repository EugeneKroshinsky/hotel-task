package com.gpsolutions.hoteltask.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactDto {
    @NotEmpty(message = "phone must not be null")
    private String phone;
    @Email
    private String email;
}
