package com.kopyshov.weatherwebapplication;

import com.kopyshov.weatherwebapplication.common.BasicServlet;
import com.kopyshov.weatherwebapplication.dao.UsersDAO;
import com.kopyshov.weatherwebapplication.entities.UserData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
                //создаем токен (слектор, валидатор)
                //сохраняем токен
                // создаем куки для селектора
                //создаем куки для валидатора
            }

            //Предыдущий код к которому вернуться если что
//            currentSessions.put(user.getUsername(), user);
//            Cookie cookie = new Cookie("username", user.getUsername());
//            response.addCookie(cookie);
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            context.setVariable("error", "Unknown login, try again");
            templateEngine.process("login", context, response.getWriter());
        }
    }

    public void destroy() {
    }
}
