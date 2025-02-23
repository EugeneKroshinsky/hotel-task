package com.gpsolutions.hoteltask.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactDto {
    @NotBlank(message = "phone must not be null")
    private String phone;
    @Email
    @NotBlank(message = "email must not be null")
    private String email;
}
