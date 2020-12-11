package com.hanyang.startup.hanyangstartup.spaceRental.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentalRoomTime {
    private int timeId;
    private int roomId;
    private LocalTime time;
    private int rentalTime;
    private int capacity;


    private boolean isDuplicate;
    private LocalDate searchDate;
}
