package com.kopyshov.weatherwebapplication.auth.dao;

import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.auth.entities.UserToken;
import com.kopyshov.weatherwebapplication.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public enum UserTokenDAO {
    INSTANCE;
    public UserToken save(UserToken token) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(token);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
