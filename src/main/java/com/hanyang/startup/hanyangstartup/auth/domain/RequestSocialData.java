package com.hanyang.startup.hanyangstartup.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestSocialData {
    private String id;
    private String name;
    private String email;
    private UserType type;
    private Role role;
}
