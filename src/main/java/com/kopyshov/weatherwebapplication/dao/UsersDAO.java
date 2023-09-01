package com.kopyshov.weatherwebapplication.dao;

import com.kopyshov.weatherwebapplication.entities.Users;
import com.kopyshov.weatherwebapplication.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public enum UsersDAO {
    INSTANCE;
    public Users find(String username, String password) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            Query<Users> query = session.createQuery("from Users where username = :username AND password = :password", Users.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            List<Users> user = query.getResultList();
            return user.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
