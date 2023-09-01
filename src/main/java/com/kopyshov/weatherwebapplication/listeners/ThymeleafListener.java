package com.kopyshov.weatherwebapplication.listeners;

import com.kopyshov.weatherwebapplication.utils.ThymeleafUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.thymeleaf.TemplateEngine;

@WebListener
public class ThymeleafListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public ThymeleafListener() {
    }
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        TemplateEngine templateEngine = ThymeleafUtil.buildTemplateEngine(servletContext);
        servletContext.setAttribute("templateEngine", templateEngine);
    }
}
