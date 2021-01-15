package com.hanyang.startup.hanyangstartup.mentoring.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CounselField {
    private int fieldId;
    private String fieldName;
    private int mentorId;
    private int formId;


}
