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
    public Response signSocialUser(@RequestBody RequestSocialData socialData,HttpServletResponse res){
        Response response;
        try{
            Map<String,Object> map = new HashMap<>();

            User user = authService.signUpSocialUser(socialData);

            final String token = jwtUtil.generateToken(user);
            final String refreshJwt = jwtUtil.generateRefreshToken(user);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, user.getUserId(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);

//            res.addCookie(accessToken);
//            res.addCookie(refreshToken);
            map.put("token",token);
            map.put("role",user.getRole());


            response = new Response("success","성공적으로 회원가입을 완료했습닌다.",map,200);
        }catch(Exception e){
            e.printStackTrace();
            response = new Response("error","회원가입 실패",e.getMessage(),400);
        }
        return response;
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

//            res.addCookie(accessToken);
//            res.addCookie(refreshToken);

            map.put("token",token);
            map.put("role",loginUser.getRole());
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

            res.addCookie(accessToken);
            res.addCookie(refreshToken);

            map.put("token",token);
            map.put("role",user.getRole());

//            Collection<String> headers = res.getHeaders(HttpHeaders.SET_COOKIE);
//            System.out.println("해");
//            for(String header:headers){
//                System.out.println("해도둘");
//                System.out.println(header);
//                res.setHeader(HttpHeaders.SET_COOKIE,header+"; "+"SameSite=None;");
//            }

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

    @PostMapping("/test")
    public Response authTest(Principal principal, HttpServletRequest req, HttpServletResponse res){
        Response response;

        try{

            response = new Response("success", "로그인에 성공했습니다.","성공",200);
        }catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", "로그인에 실패했습니다.", e.getMessage(),400);
        }
        return response;
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
