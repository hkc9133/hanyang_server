package com.hanyang.startup.hanyangstartup.board.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.auth.domain.Role;
import com.hanyang.startup.hanyangstartup.common.domain.Page;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardConfig extends Page {
    private int boardId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    private String boardEnName;
    private String boardKrName;
    private String boardDesc;
    private Boolean useFile;
    private Boolean useComment;
    private String writeRole;
    private String viewRole;
    private Boolean isPrivacy;
    private String subName01;
    private String subName02;
    private String subName03;
    private String subName04;
    private String subName05;
    private String subName06;
    private String subName07;
    private String subDValue01;
    private String subDValue02;
    private String subDValue03;
    private String subDValue04;
    private String subDValue05;
    private String subDValue06;
    private String subDValue07;

    private Integer categoryId;
    //검색용
    private Integer categoryCodeId;

}
