package com.kopyshov.weatherwebapplication.auth.dao;

import com.kopyshov.weatherwebapplication.auth.entities.UserToken;
import com.kopyshov.weatherwebapplication.utils.HibernateUtil;
import org.hibernate.Session;

public enum UserTokenDAO {
    INSTANCE;
    public void save(UserToken token) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(token);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
