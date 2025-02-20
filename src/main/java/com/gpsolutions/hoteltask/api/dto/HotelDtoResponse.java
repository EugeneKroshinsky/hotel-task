package com.gpsolutions.hoteltask.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HotelDtoResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String contactsPhone;
}
