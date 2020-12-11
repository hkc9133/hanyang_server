package com.hanyang.startup.hanyangstartup.spaceRental.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentalPlace {
    private int placeId;
    private Boolean isActive = true;
    private String placeName;
}
