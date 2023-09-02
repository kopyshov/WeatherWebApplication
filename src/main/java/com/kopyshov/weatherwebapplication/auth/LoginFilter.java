package com.kopyshov.weatherwebapplication.auth;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@WebFilter("/*")
@Slf4j
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String loginURI = req.getContextPath() + "/login";
        boolean loginRequest = req.getRequestURI().equals(loginURI);
        if(loginRequest) {
            chain.doFilter(request, response);
            return;
        }
        Cookie[] cookies = req.getCookies();
        Stream<Cookie> stream = Objects.nonNull(cookies) ? Arrays.stream(cookies) : Stream.empty();
        String cookieValue = stream.filter(cookie -> "username".equals(cookie.getName()))
                .findFirst()
                .orElse(new Cookie("username", null))
                .getValue();
        request.setAttribute("username", cookieValue);
        chain.doFilter(request, response);
    }

    public Optional<String> readCookie (HttpServletRequest request, String key){
        return Arrays.stream(request.getCookies())
                .filter(c -> key.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }
}
