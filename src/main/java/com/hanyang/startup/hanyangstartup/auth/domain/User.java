package com.hanyang.startup.hanyangstartup.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.common.domain.Page;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends Page {
    private int id;
    private String userId;

    private String userName;
    private String userPassword;

    private String userEmail;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastLogin;
    private String picture;
    private UserType type;
    private String status;
    private Role role = Role.ROLE_NOT_PERMITTED;
    private Role applyRole = Role.ROLE_NOT_PERMITTED;

    private SocialData social;
}
