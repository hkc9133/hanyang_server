package com.hanyang.startup.hanyangstartup.mentoring.domain;

import com.hanyang.startup.hanyangstartup.common.domain.Page;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mentor extends Page {
    private int mentorId;
    private String userId;
    private String mentorIntroduction;
    private MultipartFile profileImg;
    private MultipartFile profileImgFile;
    private String mentorName;
    private String mentorCompany;
    private String mentorPhoneNumber;
    private List<String> mentorKeyword;
    private String mentorKeywordStr;
    private List<Integer> mentorFieldList;
    private String mentorFieldStr;
    private String mentorEmail;
    private String mentorPosition;
    private List<String> mentorCareer;
    private String mentorCareerStr;
    private MENTOR_STATUS mentorStatus;

    private String currentCareer;
}
