package com.hanyang.startup.hanyangstartup.auth.controller;

import com.hanyang.startup.hanyangstartup.auth.domain.RequestSocialData;
import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.auth.service.AuthService;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.util.CookieUtil;
import com.hanyang.startup.hanyangstartup.util.JwtUtil;
import com.hanyang.startup.hanyangstartup.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private RedisUtil redisUtil;

//    @PostMapping("/signup/naver")
//    public Response signUpNaverUser(@RequestBody RequestSocialData socialData){
//        Response response;
//        try{
//            authService.signUpSocialUser(socialData);
//            response = new Response("success","성공적으로 회원가입을 완료했습닌다.",null);
//        }catch(Exception e){
//            e.printStackTrace();
//            response = new Response("error","회원가입 실패",e.getMessage());
//        }
//        return response;
//    }

    @PostMapping("/login/naver")
    public Response loginNaverUser(@RequestBody RequestSocialData socialData, HttpServletRequest req, HttpServletResponse res){
        Response response;
        System.out.println("로그인");
        try{

//            final User user = authService.loginSocialUser(socialData.getId(),socialData.getType());
            final User user = authService.loginSocialUser(socialData);

//            User user = new User();

            final String token = jwtUtil.generateToken(user);
            final String refreshJwt = jwtUtil.generateRefreshToken(user);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, user.getUserId(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);

            res.addCookie(accessToken);
            res.addCookie(refreshToken);

            response = new Response("success", "로그인에 성공했습니다.", token);
        }catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", "로그인에 실패했습니다.", e.getMessage());
        }
        return response;
    }

    @PostMapping("/test")
    public Response authTest(Principal principal, HttpServletRequest req, HttpServletResponse res){
        Response response;
//        System.out.println(principal.toString());
        try{

            response = new Response("success", "로그인에 성공했습니다.","성공");
        }catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", "로그인에 실패했습니다.", e.getMessage());
        }
        return response;
    }

}
