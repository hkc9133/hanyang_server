package com.hanyang.startup.hanyangstartup.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cookie[] cookies = httpServletRequest.getCookies(); // 모든 쿠키의 정보를 cookies에 저장
        if(cookies != null){ // 쿠키가 한개라도 있으면 실행
            for(int i=0; i< cookies.length; i++){
                System.out.println(cookies[i].getName());
                cookies[i].setMaxAge(0); // 유효시간을 0으로 설정
                cookies[i].setPath("/");
                httpServletResponse.addCookie(cookies[i]); // 응답 헤더에 추가
            }
        }

        httpServletResponse.setStatus(401);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        Response response = new Response("error","로그인이 되지 않은 사용자입니다....",null,401);
        PrintWriter out = httpServletResponse.getWriter();
        String jsonResponse = objectMapper.writeValueAsString(response);
        out.print(jsonResponse);
    }
}
