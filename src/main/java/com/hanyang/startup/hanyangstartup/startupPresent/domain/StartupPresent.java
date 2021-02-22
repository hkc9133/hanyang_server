package com.hanyang.startup.hanyangstartup.startupPresent.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.common.domain.Page;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StartupPresent extends Page {
    private Integer startupId;
    private String companyName;
    private String companyOwner;
    private String companyPhoneNum;
    private String companyEmail;
    private String address;
    private String gubun;
    private String companyKind;
    private String homepage;
    private String item;

    private String insta;
    private String facebook;
    private String naverBlog;
    private String twitter;
    private Boolean isBest;

    private List<Integer> businessIdList;
    private List<Integer> techIdList;

    private MultipartFile companyLogo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime regDate;


    private List<BusinessFieldCode> businessFieldList;
    private List<TechFieldCode> techFieldList;

//    private MultipartFile popupImg;
//    private MultipartFile newPopupImg;
//
    private AttachFile attachFile;
    private Integer removeFileId;
}
