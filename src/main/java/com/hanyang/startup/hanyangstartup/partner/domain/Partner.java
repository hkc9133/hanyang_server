package com.hanyang.startup.hanyangstartup.partner.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.common.domain.Page;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Partner  extends Page {
    private Integer partnerId;
    private String companyName;
    private String field;
    private String homepage;
    private String location;
    private Integer continentId;
    private ContinentCode continent;
    private MultipartFile companyLogo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime regDate;


    private AttachFile attachFile;
    private Integer removeFileId;
}
