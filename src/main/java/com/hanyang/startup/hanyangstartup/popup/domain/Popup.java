package com.hanyang.startup.hanyangstartup.popup.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.common.domain.Page;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
public class Popup extends Page {
    private Integer popupId;
    private String title;
    private String content;
    private int afterTime;
    private int leftPosition;
    private int topPosition;
    private int width;
    private int height;
    private Boolean isPc;
    private Boolean isMobile;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime today;

//    private MultipartFile popupImg;
//    private MultipartFile newPopupImg;
//
//    private List<AttachFile> AttachFileList;
}
