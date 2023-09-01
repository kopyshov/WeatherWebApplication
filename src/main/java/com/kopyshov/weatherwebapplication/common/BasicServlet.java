package com.kopyshov.weatherwebapplication.common;

import com.kopyshov.weatherwebapplication.entities.Users;
import com.kopyshov.weatherwebapplication.utils.ThymeleafUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BasicServlet extends HttpServlet {
    protected ITemplateEngine templateEngine;
    protected WebContext context;

    protected Map<String, Users> currentSessions;

    @Override
    public void init(ServletConfig config) throws ServletException {
        templateEngine = (ITemplateEngine) config.getServletContext().getAttribute("templateEngine");
        currentSessions = new ConcurrentHashMap<>();
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        context = ThymeleafUtil.buildWebContext(req, resp, getServletContext());
        try {
            super.service(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
