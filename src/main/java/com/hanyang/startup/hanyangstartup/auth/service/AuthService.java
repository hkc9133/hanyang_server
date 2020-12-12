package com.hanyang.startup.hanyangstartup.auth.service;

import com.hanyang.startup.hanyangstartup.auth.dao.AuthDao;
import com.hanyang.startup.hanyangstartup.auth.domain.RequestSocialData;
import com.hanyang.startup.hanyangstartup.auth.domain.SocialData;
import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.common.service.EmailService;
import com.hanyang.startup.hanyangstartup.util.RedisUtil;
import com.hanyang.startup.hanyangstartup.util.SaltUtil;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import javax.mail.MessagingException;
import java.util.HashMap;

@Service
public class AuthService {


    @Autowired
    private SaltUtil saltUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AuthDao authDao;

    @Autowired
    private EmailService emailService;

    @Transactional(rollbackFor = {Exception.class})
    public void signUpUser(User user) throws Exception{
        String password = user.getUserPassword();
        user.setUserPassword(saltUtil.encodePassword(saltUtil.genSalt(),password));
        authDao.signUpSocialUser(user);
    }

    @Transactional
    public User signUpSocialUser(RequestSocialData socialData){
        User newUser = new User();
        newUser.setUserId(socialData.getId());
        newUser.setUserEmail(socialData.getEmail());
        newUser.setUserName(socialData.getName());
        newUser.setRole(socialData.getRole());
        newUser.setSocial(new SocialData(socialData.getId(),socialData.getEmail(),socialData.getType()));

        System.out.println("==========");
        System.out.println(newUser);
        System.out.println("==========");
        authDao.signUpSocialUser(newUser);
        try{
            emailService.sendWelComeEmail(newUser.getSocial().getEmail(),newUser);

        }catch (MessagingException e){
            System.out.println("메일 발송 실패");
            e.printStackTrace();
        }
        System.out.println("가입함");
        return newUser;
    }

    public User findByIdAndType(RequestSocialData socialData){
        HashMap<String,Object> map  = new HashMap<>();
        map.put("id", socialData.getId());
        map.put("type", socialData.getType());
        return authDao.findByIdAndType(map);
    }

    public User findByUserId(String userId){
        return authDao.findByUserId(userId);
    }

    @Transactional
    public User loginUser(User user) throws NotFoundException {
        HashMap<String,Object> map  = new HashMap<>();
        map.put("id",user.getUserId());
        map.put("password",saltUtil.encodePassword(saltUtil.genSalt(),user.getUserPassword()));
        map.put("type","normal");

        User loginUser = authDao.findByIdAndType(map);

        if (user == null) throw new NotFoundException("멤버가 없습니다");

        return loginUser;

    }

    @Transactional
    public User loginSocialUser(RequestSocialData socialData) throws NotFoundException {
        HashMap<String,Object> map  = new HashMap<>();
        map.put("id", socialData.getId());
        map.put("type", socialData.getType());
        User user = authDao.findByIdAndType(map);

        if (user == null) throw new NotFoundException("멤버가 없습니다");

        return user;

//        if(user == null){
//            User newUser = new User();
//            newUser.setUserId(socialData.getId());
//            newUser.setUserEmail(socialData.getEmail());
//            newUser.setUserName(socialData.getName());
//            newUser.setSocial(new SocialData(socialData.getId(),socialData.getEmail(),socialData.getType()));
//
//            System.out.println(newUser);
//            authDao.signUpSocialUser(newUser);
//            System.out.println("가입함");
//            return newUser;
//        }else{
//            System.out.println("로그인만함");
//            return user;
//        }
//        if(socialData==null) throw new NotFoundException("멤버가 조회되지 않음");
////        socialData.getId()
//        return socialData.getId();
    }
}
