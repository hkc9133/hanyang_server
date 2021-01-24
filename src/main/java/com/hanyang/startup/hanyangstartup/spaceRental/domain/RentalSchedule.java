package com.hanyang.startup.hanyangstartup.spaceRental.domain;

import com.hanyang.startup.hanyangstartup.common.domain.Page;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentalSchedule extends Page {
    private int scheduleId;
    private int roomId;
    private LocalDate rentalDate;
    private LocalTime rentalStartTime;
    private LocalTime rentalEndTime;
    private String userId;
    private int personCount;
    private String purpose;
    private RENTAL_STATUS status;

    private LocalDateTime regDate;
    private String reservationNum;


    //조회
    private String userName;
    private String placeName;
    private String roomName;
    private int capacity;

}
