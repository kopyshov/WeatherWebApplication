package com.kopyshov.weatherwebapplication.dao;

import com.kopyshov.weatherwebapplication.entities.UserData;
import com.kopyshov.weatherwebapplication.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public enum UsersDAO {
    INSTANCE;
    public UserData find(String username, String password) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            Query<UserData> query = session.createQuery("from UserData where username = :username AND password = :password", UserData.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            List<UserData> user = query.getResultList();
            return user.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
