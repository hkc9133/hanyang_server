package com.hanyang.startup.hanyangstartup.startupEvent.domain;

import com.hanyang.startup.hanyangstartup.common.domain.Page;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StartupEvent extends Page {
    private int eventId;
    private PROGRESS_STATUS progressStatus;
    private LocalDateTime applyStartDate;
    private LocalDateTime applyEndDate;
    private LocalDateTime eventDate;
    private String eventName;
    private Integer categoryCodeId;
    private int fixedNumber;


    private String date;
}
