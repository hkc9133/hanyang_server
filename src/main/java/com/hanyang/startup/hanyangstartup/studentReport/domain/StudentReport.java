package com.hanyang.startup.hanyangstartup.studentReport.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.common.domain.Page;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentReport extends Page {
    private Integer studentReportId;
    private String studentName;
    private String studentAttach;
    private String studentClassYear;
    private String studentPhoneNum;
    private String studentEmail;
    private String companyNum;
    private String companyName;
    private String companyOwner;
    private String companyKind;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createDate;
    private String businessItem;
    private Integer sales;
    private Integer staffNum;

    private AttachFile certificateFile;


    private String userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    private List<AttachFile> attachFileList;
    private List<Integer> uploadResultList;


    private List<AttachFile> files;

}
