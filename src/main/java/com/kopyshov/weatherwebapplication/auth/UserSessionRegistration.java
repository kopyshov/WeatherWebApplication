package com.kopyshov.weatherwebapplication.auth;

import com.kopyshov.weatherwebapplication.auth.dao.UserTokenDAO;
import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.auth.entities.UserToken;
import com.kopyshov.weatherwebapplication.auth.utils.HashGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;

public interface UserSessionRegistration {
    default void registerUserSession(UserData user, HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);
            session.setMaxInactiveInterval(60 * 60); //one hour
            boolean rememberMe = "true".equals(request.getParameter("rememberMe"));
            if (rememberMe) {
                UserToken token = rememberUser(user, response);
                UserTokenDAO.INSTANCE.save(token);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    default UserToken rememberUser(UserData user, HttpServletResponse response) {
        String selector = RandomStringUtils.randomAlphanumeric(12);
        String rawValidator =  RandomStringUtils.randomAlphanumeric(64);
        //create Cookies
        Cookie cookieSelector = new Cookie("selector", selector);
        cookieSelector.setMaxAge(604800); //one week
        Cookie cookieValidator = new Cookie("validator", rawValidator);
        cookieValidator.setMaxAge(604800); //one week

        response.addCookie(cookieSelector);
        response.addCookie(cookieValidator);
        return generateToken(user, selector, rawValidator);
    }

    private UserToken generateToken(UserData user, String selector, String rawValidator) {
        try {
            String hashedValidator = HashGenerator.generateSHA256(rawValidator);
            return new UserToken(selector, hashedValidator, user);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
