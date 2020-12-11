package com.hanyang.startup.hanyangstartup.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

//@Component
public class CookieAttributeFilter implements Filter {
//    private Logger LOG = LoggerFactory.getLogger(SameSiteFilter.class);
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
//        LOG.info("Same Site Filter Initializing filter :{}", this);
    }
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        System.out.println("필터");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
//        LOG.info("Same Site Filter Logging Response :{}", res.getContentType());
        Collection<String> headers = res.getHeaders(HttpHeaders.SET_COOKIE);
        boolean firstHeader = true;
        for (String header : headers) { // there can be multiple Set-Cookie attributes
            if (firstHeader) {
                res.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s",  header, "SameSite=None"));
//                LOG.info(String.format("Same Site Filter First Header %s; %s", header, "SameSite=None; Secure"));
                firstHeader = false;
                continue;
            }
            res.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s",  header, "SameSite=None"));
//            LOG.info(String.format("Same Site Filter Remaining Headers %s; %s", header, "SameSite=None; Secure"));
        }
        chain.doFilter(req, res);
    }
    @Override
    public void destroy() {
//        LOG.warn("Same Site Filter Destructing filter :{}", this);
    }
}



