package com.kopyshov.weatherwebapplication.utils;

import com.kopyshov.weatherwebapplication.auth.entities.UserToken;
import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public enum HibernateUtil {
    INSTANCE;
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .addAnnotatedClass(UserData.class)
                    .addAnnotatedClass(UserToken.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }
    public void shutdown() {
        sessionFactory.close();
    }
}
