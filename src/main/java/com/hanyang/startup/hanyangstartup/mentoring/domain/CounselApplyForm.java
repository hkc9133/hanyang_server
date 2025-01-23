package com.hanyang.startup.hanyangstartup.mentoring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.common.domain.Page;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.UploadResult;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
//    private int formSortationItem;
//    private int formWayItem;
    private String formProgressItemName;
//    private String formSortationItemName;
//    private String formWayItemName;

//    private String formWayItemStr;
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
    private List<Integer> removeFiles;

    //삭제예정
//    private List<CounselField> counselFieldList;
//    private List<Integer> counselFieldIdList;
    //삭제예정

    private Integer fieldId;
    private String fieldName;

//    private List<CounselSortation> counselSortationList;
    private List<SortationItem> sortationItemList;
    private List<Integer> sortationIdList;

//    private List<CounselWay> counselWayList;
    private List<WayItem> wayItemList;
    private List<Integer> wayIdList;

    private String mentorName;
    private String mentorUserId;
    private Integer mentorId;

    //일지
    private Integer diaryId;
    private String answer;
    private String answerWay;
    private String place;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime answerDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end;
    private Integer score;

}
