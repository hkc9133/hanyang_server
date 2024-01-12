package com.hanyang.startup.hanyangstartup.util;

import com.hanyang.startup.hanyangstartup.auth.dao.AuthDao;
import com.hanyang.startup.hanyangstartup.auth.domain.SecurityMember;
import com.hanyang.startup.hanyangstartup.auth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthDao authDao;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = authDao.findByUserId(userId);
        if(user == null){
            throw new UsernameNotFoundException(userId + " : 사용자 존재하지 않음");
        }

        return new SecurityMember(user);
//        return null;
    }
}
