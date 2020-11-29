package com.hanyang.startup.hanyangstartup.auth.service;

import com.hanyang.startup.hanyangstartup.auth.dao.AuthDao;
import com.hanyang.startup.hanyangstartup.auth.domain.RequestSocialData;
import com.hanyang.startup.hanyangstartup.auth.domain.SocialData;
import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.util.RedisUtil;
import com.hanyang.startup.hanyangstartup.util.SaltUtil;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
public class AuthService {


    @Autowired
    private SaltUtil saltUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AuthDao authDao;

    @Transactional
    public void signUpSocialUser(RequestSocialData socialData){
//        User newUser = new User();
//        newUser.setUserId(socialData.getId());
//        newUser.setUserEmail(socialData.getEmail());
//        newUser.setUserName(socialData.getName());
//        newUser.setSocial(new SocialData(socialData.getId(),socialData.getEmail(),socialData.getType()));
//
//        System.out.println(newUser);
//        authDao.signUpSocialUser(newUser);
    }

    @Transactional
    public User loginSocialUser(RequestSocialData socialData) throws NotFoundException {
        HashMap<String,Object> map  = new HashMap<>();
        map.put("id", socialData.getId());
        map.put("type", socialData.getType());
        User user = authDao.findByIdAndType(map);

        if(user == null){
            User newUser = new User();
            newUser.setUserId(socialData.getId());
            newUser.setUserEmail(socialData.getEmail());
            newUser.setUserName(socialData.getName());
            newUser.setSocial(new SocialData(socialData.getId(),socialData.getEmail(),socialData.getType()));

            System.out.println(newUser);
            authDao.signUpSocialUser(newUser);
            System.out.println("가입함");
            return newUser;
        }else{
            System.out.println("로그인만함");
            return user;
        }
//        if(socialData==null) throw new NotFoundException("멤버가 조회되지 않음");
////        socialData.getId()
//        return socialData.getId();
    }
}
