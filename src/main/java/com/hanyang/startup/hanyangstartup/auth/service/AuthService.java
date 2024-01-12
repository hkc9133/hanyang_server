package com.hanyang.startup.hanyangstartup.auth.service;

import com.hanyang.startup.hanyangstartup.auth.dao.AuthDao;
import com.hanyang.startup.hanyangstartup.auth.domain.RequestSocialData;
import com.hanyang.startup.hanyangstartup.auth.domain.SocialData;
import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.common.service.EmailService;
import com.hanyang.startup.hanyangstartup.util.RedisUtil;
import com.hanyang.startup.hanyangstartup.util.SaltUtil;
import kr.ac.hanyang.service.OAuthClient;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

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
    public void test() throws Exception {
        System.out.println(saltUtil.encodePassword(saltUtil.genSalt(), "rmfrltps2871"));
    }

    @Transactional(rollbackFor = {Exception.class})
    public void signUpUser(User user) throws Exception {
        String password = user.getUserPassword();
        user.setUserPassword(saltUtil.encodePassword(saltUtil.genSalt(), password));
        authDao.signUpSocialUser(user);
    }

    @Transactional
    public User signUpSocialUser(RequestSocialData socialData) {
        User newUser = new User();
        newUser.setUserId(socialData.getId());
        newUser.setUserEmail(socialData.getEmail());
        newUser.setUserName(socialData.getName());
        newUser.setRole(socialData.getRole());
        newUser.setType(socialData.getType());
//        newUser.setSocial(new SocialData(socialData.getId(),socialData.getEmail(),socialData.getType()));

        authDao.signUpSocialUser(newUser);
        try {
            emailService.sendWelComeEmail(newUser.getUserEmail(), newUser);

        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.println("메일 발송 실패");
            e.printStackTrace();
        }
        return newUser;
    }

    public User findByIdAndType(RequestSocialData socialData) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", socialData.getId());
        map.put("type", socialData.getType());
        return authDao.findByIdAndType(map);
    }

    public User findByUserId(String userId) {
        return authDao.findByUserId(userId);
    }

    @Transactional
    public User loginUser(User user) throws NotFoundException, Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", user.getUserId());
//        map.put("password",saltUtil.encodePassword(saltUtil.genSalt(),user.getUserPassword()));
        map.put("type", "NORMAL");

        User loginUser = authDao.findByIdAndType(map);

        if (loginUser == null) throw new NotFoundException("멤버가 없습니다");

        if (!BCrypt.checkpw(user.getUserPassword(), loginUser.getUserPassword())) {
            throw new Exception("비밀번호가 틀립니다.");
        }

        authDao.updateLastLogin(loginUser);

        return loginUser;

    }

    @Transactional
    public User loginSocialUser(RequestSocialData socialData) throws NotFoundException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", socialData.getId());
        map.put("type", socialData.getType());
        User user = authDao.findByIdAndType(map);

        if (user == null) throw new NotFoundException("멤버가 없습니다");

        authDao.updateLastLogin(user);

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

    public OAuthClient initHanyang() {
        try {
            OAuthClient oAuthClient = new OAuthClient();
            oAuthClient.setClientId("5b67a3b231677f88cfcaba41b77d872");
            oAuthClient.setClientSecret("8483b416bbb356f9c6379f5dbcd8c80");
            oAuthClient.setScope("10,117");
            oAuthClient.setRedirectUrl("https://startup.hanyang.ac.kr/user/login");
//            oAuthClient.setRedirectUrl("http://127.0.0.1:3000/user/login");

            return oAuthClient;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
