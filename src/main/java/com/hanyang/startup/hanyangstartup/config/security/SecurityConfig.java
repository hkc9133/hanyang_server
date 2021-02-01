package com.hanyang.startup.hanyangstartup.config.security;

import com.hanyang.startup.hanyangstartup.util.JwtUtil;
import com.hanyang.startup.hanyangstartup.util.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/signup/**").permitAll()
                .antMatchers("/auth/login/**").permitAll()
                .antMatchers("/auth/test").hasRole("USER")
                .antMatchers("/auth/check").hasAnyRole("USER","ADMIN","SD","MT","TC")
                .antMatchers("/user/verify/**").permitAll()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/test/user").hasRole("USER")
                .antMatchers("/test/admin").hasRole("ADMIN")
                .antMatchers("/test/**").permitAll()
                .antMatchers("/auth/test/email").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/admin/**").permitAll()
                .antMatchers("/board/**").permitAll()
                .antMatchers("/space_rental").hasAnyRole("USER","ADMIN","SD","MT","TC")
                .antMatchers("/mentoring/**").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override // ignore check swagger resource
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**","/image/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
//        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
//        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With", "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://127.0.0.1:3000");
        configuration.addAllowedOrigin("https://localhost:3001");
        configuration.addAllowedOrigin("https://127.0.0.1:3001");
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("http://127.0.0.1:8080");
        configuration.addAllowedOrigin("http://210.103.188.119");
        configuration.addAllowedOrigin("http://49.50.167.168");
        configuration.addExposedHeader(JwtUtil.ACCESS_TOKEN_NAME);
        configuration.addExposedHeader(HttpHeaders.CONTENT_DISPOSITION);
        configuration.addExposedHeader("User-Id");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
