package com.hanyang.startup.hanyangstartup.spaceRental.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentalRoom {
    private int roomId;
    private int placeId;
    private String roomName;
    private Boolean isActive;
    private int capacity;
    private String possibleDay;
    private List<Integer> possibleDayArray;
    private String roomImg;
    private String roomDesc;
    private String rentalRole;
    private Boolean holidayAvailable;
}
