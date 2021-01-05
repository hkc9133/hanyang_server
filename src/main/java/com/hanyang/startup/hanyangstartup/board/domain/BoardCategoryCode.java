package com.hanyang.startup.hanyangstartup.board.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardCategoryCode {
    private int categoryCodeId;
    private int categoryId;
    private String categoryCodeName;
    private String categoryCodeDesc;

    //쿼리용
    private String categoryName;
}
