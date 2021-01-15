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

    private Integer categoryId;
    //검색용
    private Integer categoryCodeId;

}
