package com.hanyang.startup.hanyangstartup.notice.domain;

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
public class Notice extends Page {
    private int noticeId;
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
    private Boolean isNotice;


    private MultipartFile[] files;
    private List<Integer> removeFiles;

    private String date;
}
