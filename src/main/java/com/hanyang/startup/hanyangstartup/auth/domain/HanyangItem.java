package com.hanyang.startup.hanyangstartup.auth.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HanyangItem {
    private String userNm;
    private Boolean jaejikYn;
    private String loginId;
    private String userGb;
    private String sosokCd;
    private String daehakNm;
    private String userGbNm;
    private String gaeinNo;
    private String uuid;
    private String sosokId;
    private String sosokNm;
    private String email = "";

}
