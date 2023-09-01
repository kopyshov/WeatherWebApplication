package com.kopyshov.weatherwebapplication.utils;

import com.kopyshov.weatherwebapplication.entities.Users;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public enum HibernateUtil {
    INSTANCE;
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .addAnnotatedClass(Users.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }
    public void shutdown() {
        sessionFactory.close();
    }
}
