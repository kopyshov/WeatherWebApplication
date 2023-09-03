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
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.Optional;

@WebFilter("/*")
@Slf4j
public class LoginFilter implements Filter, UserSessionRegistration {
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
            if (!"".equals(selector) && !"".equals(rawValidator)) { //проверка есть TOKEN
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
//                        session = request.getSession();
//                        session.setAttribute("loggedUser", token.getUser());
//
//                        // update new token in database
//                        String newSelector = RandomStringUtils.randomAlphanumeric(12);
//                        String newRawValidator =  RandomStringUtils.randomAlphanumeric(64);
//                        String newHashedValidator = generateHashedValidator(newRawValidator);
//                        token.setSelector(newSelector);
//                        token.setValidator(newHashedValidator);
//                        UserTokenDAO.INSTANCE.update(token);
//
//                        // update cookie
//                        Cookie cookieSelector = new Cookie("selector", newSelector);
//                        cookieSelector.setMaxAge(604800);
//
//                        Cookie cookieValidator = new Cookie("validator", newRawValidator);
//                        cookieValidator.setMaxAge(604800);
//
//                        response.addCookie(cookieSelector);
//                        response.addCookie(cookieValidator);
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
