package com.hanyang.startup.hanyangstartup.auth.domain;

import org.springframework.security.core.authority.AuthorityUtils;

public class SecurityMember extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUiD = 1L;

    public SecurityMember(User user){
        super(user.getUserId(),"{noop}"+ user.getUserPassword(),AuthorityUtils.createAuthorityList(user.getRole().toString()));
    }

}
