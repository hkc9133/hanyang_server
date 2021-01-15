package com.hanyang.startup.hanyangstartup.auth.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SocialData {

    private int id;

    private String socialId;
    private String email;
    private UserType type;

    public SocialData(String socialId, String email, UserType type) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
        this.type = type;
    }
}
