package com.kopyshov.weatherwebapplication.auth.dao;

import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public enum UserDAO {
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

    public void save(UserData user) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(user);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
