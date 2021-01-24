package com.hanyang.startup.hanyangstartup.auth.controller;

import com.hanyang.startup.hanyangstartup.auth.domain.RequestSocialData;
import com.hanyang.startup.hanyangstartup.auth.domain.Role;
import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.auth.service.AuthService;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.common.service.EmailService;
import com.hanyang.startup.hanyangstartup.util.CookieUtil;
import com.hanyang.startup.hanyangstartup.util.JwtUtil;
import com.hanyang.startup.hanyangstartup.util.RedisUtil;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private EmailService emailService;
    @PostMapping("/signup")
    public Response signUser(@RequestBody User signUpUser ,HttpServletResponse res){
        Response response;
        try{
            signUpUser.setRole(Role.ROLE_ADMIN);
            authService.signUpUser(signUpUser);

            response = new Response("success","성공적으로 회원가입을 완료했습닌다.",null,200);
        }catch(Exception e){
            e.printStackTrace();
            response = new Response("error","회원가입 실패",e.getMessage(),400);
        }
        return response;
    }

    @PostMapping("/signup/social")
    public ResponseEntity<Response> signSocialUser(@RequestBody RequestSocialData socialData,HttpServletResponse res){

        System.out.println("=======!!!");
        System.out.println(socialData);
        System.out.println("=======!!!");
        Response response;
        try{
            Map<String,Object> map = new HashMap<>();

            User user = authService.signUpSocialUser(socialData);

            final String token = jwtUtil.generateToken(user);
            final String refreshJwt = jwtUtil.generateRefreshToken(user);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, user.getUserId(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);

            res.addCookie(accessToken);
            res.addCookie(refreshToken);
            map.put("token",token);
            map.put("user",user);


            response = new Response("success","성공적으로 회원가입을 완료했습닌다.",map,200);
            return new ResponseEntity(response, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            response = new Response("error","회원가입 실패",e.getMessage(),400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public Response loginUser(@RequestBody User user, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try{
            Map<String,Object> map = new HashMap<>();
            final User loginUser = authService.loginUser(user);

            final String token = jwtUtil.generateToken(user);
            final String refreshJwt = jwtUtil.generateRefreshToken(user);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, user.getUserId(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);

//            res.addHeader(JwtUtil.ACCESS_TOKEN_NAME,token);

            res.addCookie(accessToken);
            res.addCookie(refreshToken);

            map.put("user",user);
            map.put("token",token);
            loginUser.setUserPassword("");
            response = new Response("success", "로그인에 성공했습니다.", map,200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", "로그인에 실패했습니다.", e.getMessage(),400);
        }
        return response;
    }

    @PostMapping("/login/social")
    public Response loginSocialUser(@RequestBody RequestSocialData socialData, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try{
            Map<String,Object> map = new HashMap<>();
            final User user = authService.loginSocialUser(socialData);

            final String token = jwtUtil.generateToken(user);
            final String refreshJwt = jwtUtil.generateRefreshToken(user);

            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, user.getUserId(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);

//            res.addHeader(JwtUtil.ACCESS_TOKEN_NAME,token);
//
            res.addCookie(accessToken);
            res.addCookie(refreshToken);

            user.setUserPassword("");
            map.put("token",token);
            map.put("user",user);


            user.setUserPassword("");

            response = new Response("success", "로그인에 성공했습니다.", map,200);
        }catch (NotFoundException e){
            response =  new Response("error", "회원가입 필요", socialData,401);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", "로그인에 실패했습니다.", e.getMessage(),400);
        }
        return response;
    }

    @PostMapping("/duplicate_check")
    public Response duplicate_check(@RequestBody RequestSocialData socialData, HttpServletRequest req, HttpServletResponse res){
        Response response;

        try{
            User user = authService.findByIdAndType(socialData);
            if(user != null){
                response = new Response("success", "중복 안됨",null ,200);

            }else{
                response = new Response("success", "중복 됨",null, 409);
            }
        }catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", "체크 실패", e.getMessage(),400);
        }
        return response;
    }

    @PostMapping("/check")
    public Response authTest(Principal principal, HttpServletRequest req, HttpServletResponse res){
        Response response;

        User user = authService.findByUserId(principal.getName());
        Map<String,Object> map = new HashMap<>();
//        final User user = authService.get

        final String token = jwtUtil.generateToken(user);
        final String refreshJwt = jwtUtil.generateRefreshToken(user);
        Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
        Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
//        redisUtil.setDataExpire(refreshJwt, user.getUserId(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);

//        res.addCookie(accessToken);
//        res.addCookie(refreshToken);

        user.setUserPassword("");

        try{

            response = new Response("success", "인증 성공",user,200);
        }catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", "인증 실패.", e.getMessage(),400);
        }
        return response;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(Principal principal, HttpServletRequest req, HttpServletResponse res){
        Response response;

        try {
            Cookie[] cookies = req.getCookies(); // 모든 쿠키의 정보를 cookies에 저장
            if(cookies != null){ // 쿠키가 한개라도 있으면 실행
                for(int i=0; i< cookies.length; i++){
                    System.out.println(cookies[i].getName());
                    cookies[i].setMaxAge(0); // 유효시간을 0으로 설정
                    cookies[i].setPath("/");
                    res.addCookie(cookies[i]); // 응답 헤더에 추가
                }
            }

            res.setHeader(JwtUtil.ACCESS_TOKEN_NAME,"");

            return new ResponseEntity<>(new Response("success", "로그아웃 완료",null,200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("error", "로그인에 실패",null,500), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/test/email")
    public void emailTest(Principal principal, HttpServletRequest req, HttpServletResponse res){
        User user = new User();
        user.setUserName("HKC");
        try{
            System.out.println("메일 발송");
            emailService.sendWelComeEmail("hkc9133@naver.com", user);

        }catch (MessagingException e){
            System.out.println("메일 발송 실패");
            e.printStackTrace();
        }
    }

}
