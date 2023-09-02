package com.kopyshov.weatherwebapplication.auth.servlets;

import com.kopyshov.weatherwebapplication.auth.dao.UserTokenDAO;
import com.kopyshov.weatherwebapplication.auth.utils.HashGenerator;
import com.kopyshov.weatherwebapplication.common.BasicServlet;
import com.kopyshov.weatherwebapplication.auth.dao.UserDAO;
import com.kopyshov.weatherwebapplication.auth.entities.UserToken;
import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;

@WebServlet({"/login"})
public class LoginServlet extends BasicServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        templateEngine.process("login", context, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserData user = UserDAO.INSTANCE.find(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);
            session.setMaxInactiveInterval(60*60);

            boolean rememberMe = "true".equals(request.getParameter("rememberMe"));
            if (rememberMe) {
                String selector = RandomStringUtils.randomAlphanumeric(12);
                String rawValidator =  RandomStringUtils.randomAlphanumeric(64);

                UserToken token = generateToken(user, selector, rawValidator);
                UserTokenDAO.INSTANCE.save(token);

                //create Cookies
                Cookie cookieSelector = new Cookie("selector", selector);
                cookieSelector.setMaxAge(604800);
                Cookie cookieValidator = new Cookie("validator", rawValidator);
                cookieValidator.setMaxAge(604800);

                response.addCookie(cookieSelector);
                response.addCookie(cookieValidator);
            }
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            context.setVariable("error", "Unknown login, try again");
            templateEngine.process("login", context, response.getWriter());
        }
    }

    private static UserToken generateToken(UserData user, String selector, String rawValidator) {
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

    public void destroy() {
    }
}
