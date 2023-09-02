package com.kopyshov.weatherwebapplication.auth.dao;

import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.auth.entities.UserToken;
import com.kopyshov.weatherwebapplication.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

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

    public UserToken findBySelector(String selector) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            Query<UserToken> query = session.createQuery("from UserToken where selector = :selector", UserToken.class);
            query.setParameter("selector", selector);
            List<UserToken> tokens = query.getResultList();
            return tokens.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(UserToken token) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.merge(token);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(UserToken token) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.remove(token);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
