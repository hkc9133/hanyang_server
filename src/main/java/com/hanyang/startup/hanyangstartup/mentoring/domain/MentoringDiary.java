package com.hanyang.startup.hanyangstartup.mentoring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MentoringDiary {
    private Integer diaryId;
    private Integer formId;
    private Integer mentorId;
    private String mentorUserId;
    private String menteeUserId;
    private Integer score;
    private String answer;
    private String way;
    private List<Integer> wayIdList;
    private String place;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end;


    private MultipartFile[] files;
    private List<Integer> removeFiles;


    //result 용도
    private List<AttachFile> attachFileList;

}
