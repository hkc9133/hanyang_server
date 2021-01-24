package com.hanyang.startup.hanyangstartup.spaceRental.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentalRoomTime {
    private Integer timeId;
    private Integer roomId;
    @DateTimeFormat(pattern = "HH:mm")
    private String startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private String endTime;
//    private Integer rentalTime;

    private boolean isDuplicate;
    private LocalDate searchDate;
}
