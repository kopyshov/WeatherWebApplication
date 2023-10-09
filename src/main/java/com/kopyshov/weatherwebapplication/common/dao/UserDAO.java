package com.kopyshov.weatherwebapplication.common.dao;

import com.kopyshov.weatherwebapplication.common.entities.UserData;
import com.kopyshov.weatherwebapplication.utils.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

public enum UserDAO {
    INSTANCE;

    public Optional<UserData> find(String username) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            Query<UserData> query = session.createNamedQuery("findByUsername", UserData.class);
            query.setParameter("username", username);
            return query.uniqueResultOptional();
        }
    }

    public UserData findById(Long userId) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            return session.find(UserData.class, userId);
        }
    }

    public void save(UserData user) throws PersistenceException {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(user);
            session.flush();
            session.getTransaction().commit();
        }
    }
}
