package com.hanyang.startup.hanyangstartup.keyword.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.common.domain.Page;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Keyword extends Page {
    private Integer keywordId;
    private String keyword;
    private Integer count;
//    private MultipartFile popupImg;
//    private MultipartFile newKeywordImg;
//
//    private List<AttachFile> AttachFileList;
}
