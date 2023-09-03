package com.kopyshov.weatherwebapplication.auth;

import com.kopyshov.weatherwebapplication.auth.dao.UserTokenDAO;
import com.kopyshov.weatherwebapplication.auth.entities.UserToken;
import com.kopyshov.weatherwebapplication.auth.utils.HashGenerator;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@WebFilter("/*")
@Slf4j
public class LoginFilter implements Filter {
    public void init(FilterConfig config) {
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("loggedUser") != null;
        Cookie[] cookies = request.getCookies();
        if(!loggedIn && cookies != null) { //если не залогирован и есть куки
            String selector = "";
            String rawValidator = "";
            for (Cookie aCookie : cookies) {
                if (aCookie.getName().equals("selector")) {
                    selector = aCookie.getValue();
                } else if (aCookie.getName().equals("validator")) {
                    rawValidator = aCookie.getValue();
                }
            }
            if (!"".equals(selector) && !"".equals(rawValidator)) { //проверка есть ли TOKEN
                Optional<UserToken> foundedToken = UserTokenDAO.INSTANCE.findBySelector(selector);
                if (foundedToken.isPresent()) {
                    UserToken token = foundedToken.get();
                    String hashedValidatorDatabase = token.getValidator();
                    String hashedValidatorCookie = generateHashedValidator(rawValidator);

                    if (hashedValidatorCookie.equals(hashedValidatorDatabase)) {
                        LoginService loginService = new LoginService();
                        try {
                            loginService.openAccessByToken(token.getUser(), request, response);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
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
