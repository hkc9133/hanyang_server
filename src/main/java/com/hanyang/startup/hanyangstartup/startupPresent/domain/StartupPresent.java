package com.hanyang.startup.hanyangstartup.startupPresent.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.common.domain.Page;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StartupPresent extends Page {
    private Integer startupPresentId;
    private String companyName;
    private String item;
    private String keyword;
    private String homepage;
    //학생/교사
    private String group;
    //분야
    private int fieldId;
    private String fieldName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime regDate;


//    private MultipartFile popupImg;
//    private MultipartFile newPopupImg;
//
//    private List<AttachFile> AttachFileList;
}
