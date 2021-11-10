package com.hanyang.startup.hanyangstartup.startup_calendar.domain;

import com.hanyang.startup.hanyangstartup.common.domain.Page;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StartupCalendar extends Page {
    private int startupCalendarId;
    private String title;
    private String content;
    private PROGRESS_STATUS progressStatus;
    private LocalDateTime applyStartDate;
    private LocalDateTime applyEndDate;
    private LocalDateTime eventDate;
    private String applyStartDateStr;
    private String applyEndDateStr;
    private String eventDateStr;
    private LocalDateTime regDate;
    private Integer categoryCodeId;
    private String categoryCodeName;
    private Integer viewCnt;
    private String writerId;
    private String boardEnName;
    private String fileExtension;


    private MultipartFile[] files;
    private List<Integer> removeFiles;

    private String date;
}
