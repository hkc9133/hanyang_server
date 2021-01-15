package com.hanyang.startup.hanyangstartup.mentoring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import lombok.*;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    private MultipartFile[] files;
    private List<Integer> removeFiles;


    //result 용도
    private List<AttachFile> attachFileList;

}
