package com.hanyang.startup.hanyangstartup.board.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardCategory {
    private int categoryId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    private String categoryEnName;
    private String categoryKrName;
    private String boardEnName;
    private Boolean isActive;
}
