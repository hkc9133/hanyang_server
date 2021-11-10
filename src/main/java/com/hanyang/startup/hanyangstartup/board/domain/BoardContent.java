package com.hanyang.startup.hanyangstartup.board.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.common.domain.Page;
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
public class BoardContent  extends Page {
    private Integer contentId;
    private String title;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime regDate;
    private String thumbnailImg;
    private String writerId;
    private String boardEnName;
    private Integer categoryCodeId;
    private String fileExtension;
    private int viewCnt;
    private Boolean isNotice = false;
    private String sub01 = "";
    private String sub02 = "";
    private String sub03 = "";
    private String sub04 = "";
    private String sub05 = "";
    private String sub06 = "";
    private String sub07 = "";


    private MultipartFile[] thumb;
    private MultipartFile[] files;
    private List<Integer> removeFiles;


    //result 용도
    private List<AttachFile> attachFileList;
    private List<AttachFile> thumbList;
    private String categoryCodeName;
    private String userName;
    private String color;
    private Integer replyCount;

}
