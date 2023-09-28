package com.kopyshov.weatherwebapplication.auth.servlets;

import com.kopyshov.weatherwebapplication.auth.AuthService;
import com.kopyshov.weatherwebapplication.BasicServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;

public abstract class AuthServlet extends BasicServlet {
    protected AuthService authService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        authService = new AuthService();
        super.init(config);
    }
}
