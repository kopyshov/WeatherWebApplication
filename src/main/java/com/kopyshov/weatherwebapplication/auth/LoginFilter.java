package com.kopyshov.weatherwebapplication.auth;

import com.kopyshov.weatherwebapplication.common.dao.UserTokenDAO;
import com.kopyshov.weatherwebapplication.common.entities.UserToken;
import com.kopyshov.weatherwebapplication.auth.utils.HashGenerator;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebFilter("/*")
@Slf4j
public class LoginFilter implements Filter, MappingCookies {
    public void init(FilterConfig config) {
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("loggedUser") != null;
        request.setAttribute("rememberMe", false);

        Map<String, String> cookies = mapCookies(request);

        if(!loggedIn && !cookies.isEmpty()) { //если не залогирован и есть куки
            if (cookies.containsKey("selector") && cookies.containsKey("validator")) { //в куки есть токен?
                Optional<UserToken> savedToken = UserTokenDAO.INSTANCE.findBySelector(cookies.get("selector"));
                if (savedToken.isPresent()) { //есть ли Token в БД
                    String hashedValidatorDatabase = savedToken.get().getValidator(); //валидатор из БД
                    String hashedValidatorCookie = generateHashedValidator(cookies.get("validator")); //валидатор request
                    if(hashedValidatorCookie.equals(hashedValidatorDatabase)) { //проверяем валидность
                        request.setAttribute("rememberMe", true); //устанавливаем параметр для обновления токена
                    }
                }
            }
        }
        chain.doFilter(req, resp);
    }

    private String generateHashedValidator(String rawValidator) {
        try {
            return HashGenerator.generateSHA256(rawValidator);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
