package com.kopyshov.weatherwebapplication.auth;

import com.kopyshov.weatherwebapplication.auth.utils.HashGenerator;
import com.kopyshov.weatherwebapplication.common.dao.UserTokenDAO;
import com.kopyshov.weatherwebapplication.common.entities.UserData;
import com.kopyshov.weatherwebapplication.common.entities.UserToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;

public class AuthService {
    public void openAccess(UserData user, HttpServletRequest request, HttpServletResponse response) {
        //новая сессия, так как сюда попадаешь только если нужна новая сессия
        openUserSession(user, request);
        //RememberMe?
        boolean rememberMe = "true".equals(request.getParameter("rememberMe"))
                || (boolean) request.getAttribute("rememberMe");
        if (rememberMe) {
            generateUserToken(user, response);
        }
    }

    public void closeAccess(String selector, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("loggedUser");
        Optional<UserToken> token = UserTokenDAO.INSTANCE.findBySelector(selector);
        if (token.isPresent()) { //удаляем токен если он существует
            UserTokenDAO.INSTANCE.delete(token.get());
            Cookie cookieSelector = new Cookie("selector", "");
            cookieSelector.setMaxAge(0);
            Cookie cookieValidator = new Cookie("validator", "");
            cookieValidator.setMaxAge(0);
            response.addCookie(cookieSelector);
            response.addCookie(cookieValidator);
        }
    }


    private void openUserSession(UserData user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("loggedUser", user);
        session.setMaxInactiveInterval(60 * 60); //one hour
    }

    private void generateUserToken(UserData user, HttpServletResponse response) {
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
        UserToken token = new UserToken(user, selector, hashedValidator);
        UserTokenDAO.INSTANCE.save(token);
    }
}
