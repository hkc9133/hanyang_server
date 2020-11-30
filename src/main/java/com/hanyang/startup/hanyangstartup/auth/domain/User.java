package com.hanyang.startup.hanyangstartup.auth.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int id;
    private String userId;

    private String userName;
    private String userPassword;

    private String userEmail;

    private String picture;

    private Role role = Role.ROLE_NOT_PERMITTED;

    private SocialData social;
}
