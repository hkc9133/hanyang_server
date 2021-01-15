package com.hanyang.startup.hanyangstartup.board.domain;

import com.hanyang.startup.hanyangstartup.common.domain.Page;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reply extends Page {
    private Integer replyId;
    private Integer contentId;
    private Integer parentId;
    private Integer parentReplyId;
    private String replyContent;
    private String replyWriter;
    private String status;
    private LocalDateTime regDate;

    //작성자 이름
    private String userName;
    //대댓글일때 상위 댓글의 작성자 이름
    private String toName;

}
