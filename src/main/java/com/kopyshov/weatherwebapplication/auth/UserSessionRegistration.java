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
        HttpSession session = request.getSession();
        session.setAttribute("loggedUser", user);
        session.setMaxInactiveInterval(60*60); //one hour
        boolean rememberMe = "true".equals(request.getParameter("rememberMe"));
        if (rememberMe) {
            rememberUser(user, response);
        }
    }

    private void rememberUser(UserData user, HttpServletResponse response) {
        String selector = RandomStringUtils.randomAlphanumeric(12);
        String rawValidator =  RandomStringUtils.randomAlphanumeric(64);

        UserToken token = generateToken(user, selector, rawValidator);
        UserTokenDAO.INSTANCE.save(token);

        //create Cookies
        Cookie cookieSelector = new Cookie("selector", selector);
        cookieSelector.setMaxAge(604800); //one week
        Cookie cookieValidator = new Cookie("validator", rawValidator);
        cookieValidator.setMaxAge(604800); //one week

        response.addCookie(cookieSelector);
        response.addCookie(cookieValidator);
    }

    private UserToken generateToken(UserData user, String selector, String rawValidator) {
        UserToken token = new UserToken();

        String hashedValidator = "";
        try {
            hashedValidator = HashGenerator.generateSHA256(rawValidator);
        } catch (Exception e) {
            e.printStackTrace();
        }

        token.setSelector(selector);
        token.setValidator(hashedValidator);
        token.setUser(user);

        return token;
    }
}
