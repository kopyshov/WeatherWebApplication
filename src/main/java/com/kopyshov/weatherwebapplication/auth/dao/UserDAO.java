package com.kopyshov.weatherwebapplication.auth.dao;

import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.buisness.Location;
import com.kopyshov.weatherwebapplication.utils.HibernateUtil;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import java.util.Optional;

public enum UserDAO {
    INSTANCE;
    public Optional<UserData> find(String username, String password) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            Query<UserData> query = session.createNamedQuery("findByUsernameAndPass", UserData.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.uniqueResultOptional();
        }
    }

    public UserData findById(Long userId) {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            return session.find(UserData.class, userId);
        }
    }

    public void save(UserData user) throws PersistenceException, ConstraintViolationException {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(user);
            session.flush();
            session.getTransaction().commit();
        }
    }

    public void addLocationToUser(UserData user, Location location) throws PersistenceException, ConstraintViolationException  {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            UserData userData = session.find(UserData.class, user.getId());
            userData.getAdded().add(location);
            session.merge(userData);
            session.getTransaction().commit();
        }
    }
}
