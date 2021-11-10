package com.hanyang.startup.hanyangstartup.spaceRental.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.common.domain.Page;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Integer roomId;
    private LocalDate rentalDate;
    @JsonFormat(pattern = "kk:mm")
    private LocalTime rentalStartTime;
    @JsonFormat(pattern = "kk:mm")
    private LocalTime rentalEndTime;
    private String userId;
    private int personCount;
    private String purpose;
    private RENTAL_STATUS status;

    private LocalDateTime regDate;
    private String reservationNum;


    //조회
    private String userName;
    private String userPhoneNum;
    private String userCompany;

    private String placeName;
    private String roomName;
    private int capacity;

    private String regStartDate;
    private String regEndDate;
    private String rentalStartDate;
    private String rentalEndDate;

    //캘린더에서 사용
    private String Date;
}
