package com.kopyshov.weatherwebapplication.auth.dao;

import com.kopyshov.weatherwebapplication.auth.entities.UserToken;
import com.kopyshov.weatherwebapplication.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

public enum UserTokenDAO {
    INSTANCE;
    public void save(UserToken token) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.merge(token);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<UserToken> findBySelector(String selector) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            Query<UserToken> query = session.createNamedQuery("findBySelector", UserToken.class);
            query.setParameter("selector", selector);
            return query.uniqueResultOptional();
        }
    }

    public void update(UserToken token) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.merge(token);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(UserToken token) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.remove(token);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
