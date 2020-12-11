package com.hanyang.startup.hanyangstartup.spaceRental.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentalSchedule {
    private int scheduleId;
    private int roomId;
    private LocalDate rentalDate;
    private LocalTime rentalStartTime;
    private LocalTime rentalEndTime;
    private String userId;
    private int personCount;
    private String purpose;
    private String progressStatus;

    private LocalDate regDate;
}
