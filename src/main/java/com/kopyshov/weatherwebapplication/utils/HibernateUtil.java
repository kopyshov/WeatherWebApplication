package com.kopyshov.weatherwebapplication.utils;

import com.kopyshov.weatherwebapplication.common.entities.Location;
import com.kopyshov.weatherwebapplication.common.entities.UserData;
import com.kopyshov.weatherwebapplication.common.entities.UserToken;
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
                    .addAnnotatedClass(Location.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public void shutdown() {
        sessionFactory.close();
    }
}
