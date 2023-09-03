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

public class LoginService {
    public LoginService() {
    }
    public void openAccess(UserData user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //новая сессия, так как сюда попадаешь только если нужна новая сессия
        openUserSession(user, request);
        //RememberMe?
        boolean rememberMe = "true".equals(request.getParameter("rememberMe"));
        if (rememberMe) {
            generateUserToken(user, response);
        }
    }

    public void openAccessByToken(UserData user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //новая сессия, так как сюда попадаешь только если нужна новая сессия
        openUserSession(user,request);
        //обновляем token
        generateUserToken(user, response);
    }
    private static void openUserSession(UserData user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("loggedUser", user);
        session.setMaxInactiveInterval(60 * 60); //one hour
    }

    private static void generateUserToken(UserData user, HttpServletResponse response) throws Exception {
        String selector = RandomStringUtils.randomAlphanumeric(12);
        String rawValidator = RandomStringUtils.randomAlphanumeric(64);
        //create Cookies
        Cookie cookieSelector = new Cookie("selector", selector);
        cookieSelector.setMaxAge(604800); //one week
        Cookie cookieValidator = new Cookie("validator", rawValidator);
        cookieValidator.setMaxAge(604800); //one week

        response.addCookie(cookieSelector);
        response.addCookie(cookieValidator);
        String hashedValidator = HashGenerator.generateSHA256(rawValidator);
        UserToken token = new UserToken(selector, hashedValidator, user);
        UserTokenDAO.INSTANCE.save(token);
    }
}
