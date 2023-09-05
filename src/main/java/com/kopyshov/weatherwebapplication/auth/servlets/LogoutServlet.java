package com.kopyshov.weatherwebapplication.auth.servlets;

import com.kopyshov.weatherwebapplication.auth.MappingCookies;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/logout")
public class LogoutServlet extends AuthServlet implements MappingCookies {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> cookies = mapCookies(request);
        if (cookies.containsKey("selector")) {
            authService.closeAccess(cookies.get("selector"), request, response);
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
