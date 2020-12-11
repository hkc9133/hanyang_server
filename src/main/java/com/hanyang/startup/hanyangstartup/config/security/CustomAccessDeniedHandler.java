package com.hanyang.startup.hanyangstartup.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanyang.startup.hanyangstartup.auth.domain.Role;
import com.hanyang.startup.hanyangstartup.auth.domain.SecurityMember;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();

        httpServletResponse.setStatus(403);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        Response response = new Response("error","접근가능한 권한을 가지고 있지 않습니다.",null,403);

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        SecurityMember member = (SecurityMember)authentication.getPrincipal();
//        Collection<GrantedAuthority> authorities = member.getAuthorities();

//        if(hasRole(authorities, Role.ROLE_NOT_PERMITTED.name())){
//            response.setMessage("사용자 인증메일을 받지 않았습니다.");
//        }

        PrintWriter out = httpServletResponse.getWriter();
        String jsonResponse = objectMapper.writeValueAsString(response);
        out.print(jsonResponse);

    }

    private boolean hasRole(Collection<GrantedAuthority> authorites, String role){
        return authorites.contains(new SimpleGrantedAuthority(role));
    }

}
