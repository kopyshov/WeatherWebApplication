package com.kopyshov.weatherwebapplication.auth.servlets;

import com.kopyshov.weatherwebapplication.auth.dao.UserTokenDAO;
import com.kopyshov.weatherwebapplication.auth.entities.UserToken;
import com.kopyshov.weatherwebapplication.common.BasicServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends BasicServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("loggedUser");

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            String selector = "";

            for (Cookie aCookie : cookies) {
                if (aCookie.getName().equals("selector")) {
                    selector = aCookie.getValue();
                }
            }

            if (!selector.isEmpty()) {
                // delete token from database
                UserToken token = UserTokenDAO.INSTANCE.findBySelector(selector);

                if (token != null) {
                    UserTokenDAO.INSTANCE.delete(token);

                    Cookie cookieSelector = new Cookie("selector", "");
                    cookieSelector.setMaxAge(0);

                    Cookie cookieValidator = new Cookie("validator", "");
                    cookieValidator.setMaxAge(0);
                    response.addCookie(cookieSelector);
                    response.addCookie(cookieValidator);
                }
            }
        }
        response.sendRedirect(request.getContextPath());
    }
}
