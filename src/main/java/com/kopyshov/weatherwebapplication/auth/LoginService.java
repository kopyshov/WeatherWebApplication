package com.kopyshov.weatherwebapplication.auth;

import com.kopyshov.weatherwebapplication.auth.dao.UserDAO;
import com.kopyshov.weatherwebapplication.auth.dao.UserTokenDAO;
import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.auth.entities.UserToken;
import com.kopyshov.weatherwebapplication.auth.utils.HashGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.Optional;

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

    /*public void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Optional<UserData> user = UserDAO.INSTANCE.find(username, password);
        if (user.isPresent()){
            try {
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", user);
                session.setMaxInactiveInterval(60 * 60); //one hour
                boolean rememberMe = "true".equals(request.getParameter("rememberMe"));
                if (rememberMe) {
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
                    UserToken token = new UserToken(selector, hashedValidator, user.get());
                    UserTokenDAO.INSTANCE.save(token);
                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }*/

    /*public void loginUserByToken(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("loggedUser") != null;
        Cookie[] cookies = request.getCookies();
        if(!loggedIn && cookies != null) {
            String selector = "";
            String rawValidator = "";
            for (Cookie aCookie : cookies) {
                if (aCookie.getName().equals("selector")) {
                    selector = aCookie.getValue();
                } else if (aCookie.getName().equals("validator")) {
                    rawValidator = aCookie.getValue();
                }
            }

            if (!"".equals(selector) && !"".equals(rawValidator)) {
                Optional<UserToken> foundedToken = UserTokenDAO.INSTANCE.findBySelector(selector);
                if (foundedToken.isPresent()) {
                    UserToken token = foundedToken.get();
                    String hashedValidatorDatabase = token.getValidator();
                    String hashedValidatorCookie = generateHashedValidator(rawValidator);

                    if (hashedValidatorCookie.equals(hashedValidatorDatabase)) {
                        session = request.getSession();
                        session.setAttribute("loggedUser", token.getUser());

                        // update new token in database
                        String newSelector = RandomStringUtils.randomAlphanumeric(12);
                        String newRawValidator =  RandomStringUtils.randomAlphanumeric(64);
                        String newHashedValidator = generateHashedValidator(newRawValidator);
                        token.setSelector(newSelector);
                        token.setValidator(newHashedValidator);
                        UserTokenDAO.INSTANCE.update(token);

                        // update cookie
                        Cookie cookieSelector = new Cookie("selector", newSelector);
                        cookieSelector.setMaxAge(604800);

                        Cookie cookieValidator = new Cookie("validator", newRawValidator);
                        cookieValidator.setMaxAge(604800);

                        response.addCookie(cookieSelector);
                        response.addCookie(cookieValidator);
                    }
                }
            }
        }
    }*/
/*    private String generateHashedValidator(String rawValidator) {
        try {
            return HashGenerator.generateSHA256(rawValidator);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }*/
}
