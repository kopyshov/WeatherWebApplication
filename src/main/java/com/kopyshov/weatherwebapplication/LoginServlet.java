package com.kopyshov.weatherwebapplication;

import com.kopyshov.weatherwebapplication.auth.utils.HashGenerator;
import com.kopyshov.weatherwebapplication.common.BasicServlet;
import com.kopyshov.weatherwebapplication.dao.UsersDAO;
import com.kopyshov.weatherwebapplication.entities.UserAuth;
import com.kopyshov.weatherwebapplication.entities.UserData;
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

        boolean rememberMe = "true".equals(request.getParameter("rememberMe"));

        UserData user = UsersDAO.INSTANCE.find(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);

            if (rememberMe) {
                //создаем токен (селектор, валидатор)
                generateToken(user);
                //сохраняем токен
                // создаем куки для селектора
                //создаем куки для валидатора
            }
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            context.setVariable("error", "Unknown login, try again");
            templateEngine.process("login", context, response.getWriter());
        }
    }

    private static void generateToken(UserData user) {
        UserAuth token = new UserAuth();

        String selector = RandomStringUtils.randomAlphanumeric(12);
        String rawValidator =  RandomStringUtils.randomAlphanumeric(64);

        String hashedValidator = "";
        try {
            hashedValidator = HashGenerator.generateSHA256(rawValidator);
        } catch (Exception e) {
            e.printStackTrace();
        }

        token.setSelector(selector);
        token.setValidator(hashedValidator);

        token.setUser(user);
    }

    public void destroy() {
    }
}
