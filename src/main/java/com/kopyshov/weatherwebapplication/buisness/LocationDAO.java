package com.kopyshov.weatherwebapplication.buisness;

import com.kopyshov.weatherwebapplication.utils.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;

public enum LocationDAO {
    INSTANCE;
    public void save(Location location) throws PersistenceException {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.merge(location);
            session.getTransaction().commit();
        }
    }
}
