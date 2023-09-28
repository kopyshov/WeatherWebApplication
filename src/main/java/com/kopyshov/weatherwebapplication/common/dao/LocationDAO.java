package com.kopyshov.weatherwebapplication.common.dao;

import com.kopyshov.weatherwebapplication.common.entities.UserData;
import com.kopyshov.weatherwebapplication.common.entities.Location;
import com.kopyshov.weatherwebapplication.utils.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import java.util.Optional;

public enum LocationDAO {
    INSTANCE;
    public void addLocationToUser(UserData user, Location location) throws PersistenceException, ConstraintViolationException {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            UserData userData = session.find(UserData.class, user.getId());
            Query<Location> query = session.createNamedQuery("findByCoordinates", Location.class);
            query.setParameter("latitude", location.getLatitude());
            query.setParameter("longitude", location.getLongitude());
            Optional<Location> fLocation = query.uniqueResultOptional();
            if (fLocation.isPresent()) {
                userData.addLocation(fLocation.get());
            } else {
                userData.addLocation(location);
            }
            session.merge(userData);
            session.getTransaction().commit();
        }
    }

    public void removeLocationFromUser(UserData user, String locationId) throws PersistenceException, ConstraintViolationException {
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            UserData userData = session.find(UserData.class, user.getId());
            Location location = session.find(Location.class, locationId);
            userData.removeLocation(location);
            session.merge(userData);
            session.getTransaction().commit();
        }
    }
}
