package com.hanyang.startup.hanyangstartup.mentoring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.common.domain.Page;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.UploadResult;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CounselApplyForm extends Page {
    private int formId;
    private String userId;
    private String title;
    private String content;
    private String formNum;
    private int formProgressItem;
    private int formSortationItem;
    private int formWayItem;
    private String formProgressItemName;
    private String formSortationItemName;
    private String formWayItemName;
    private String formWayItemStr;
    private String menteeName;
    private String menteeEmail;
    private Integer hopeMentor;
    private Integer assignMentorId;
    private String menteePhoneNumber;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime regDate;
    private APPLY_STATUS applyStatus;
    private List<APPLY_STATUS> deactivateApplyStatusList;

    private List<AttachFile> attachFileList;
    private List<Integer> uploadResultList;

    private List<CounselField> counselFieldList;
    private List<Integer> counselFieldIdList;

    private String mentorName;
    private String mentorUserId;
    private Integer mentorId;

    //일지
    private Integer diaryId;
    private String answer;
    private String answerWay;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime answerDate;
    private Integer score;

}
