package com.hanyang.startup.hanyangstartup.board.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.common.domain.Page;
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
public class BoardContent  extends Page {
    private Integer contentId;
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime regDate;
    private String thumbnailImg;
    private String writerId;
    private String boardEnName;
    private Integer categoryCodeId;
    private String fileExtension;
    private int viewCnt;
    private Boolean isNotice = false;


    private MultipartFile[] files;
    private List<Integer> removeFiles;


    //result 용도
    private List<AttachFile> attachFileList;
    private String categoryCodeName;
    private String userName;
    private String color;

}
