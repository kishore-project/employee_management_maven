package com.ideas2it.sports.dao;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.exceptions.EmployeeException;
import com.ideas2it.model.Employee;
import com.ideas2it.model.Sport;
import com.ideas2it.utilities.HibernateConnection;

/**
 * Repository for managing sport entities. This class provides methods to add, delete, update,
 * and retrieve sports.
 * @author  Kishore 
 * @version 1.0 
 */
public class SportRepositoryImpl implements SportRepository {

    private static final Logger logger = LogManager.getLogger(SportRepositoryImpl.class);

    @Override
    public void addSport(Sport sport) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            session.save(sport);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while adding Sport: " + sport.getName(), e);
            throw new EmployeeException("Error while adding Sport: " + sport.getName(), e);
        }
    }

    @Override
    public void deleteSport(int id) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            Sport sport = session.get(Sport.class, id);
            if (sport != null) {
                sport.setIsActive(false);
                session.update(sport);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while deleting sport: " + id, e);
            throw new EmployeeException("Error while deleting sport: " + id, e);
        }
    }

    @Override
    public Set<Sport> getAllSports() throws EmployeeException {
        try (Session session = HibernateConnection.getSession()) {
            Query<Sport> query = session.createQuery("FROM Sport WHERE isActive = true", Sport.class);
            return new HashSet<>(query.list());
        } catch (HibernateException e) {
            logger.info("Error while getting all sports", e);
            throw new EmployeeException("Error while getting all sports", e);
        }
    }

    @Override
    public Sport findSportById(int id) throws EmployeeException {
        try (Session session = HibernateConnection.getSession()) {
            return session.get(Sport.class, id);
        } catch (HibernateException e) {
            logger.error("Error while getting sport by ID: " + id, e);
            throw new EmployeeException("Error while getting sport by ID: " + id, e);
        }
    }

    @Override
    public void updateSport(Sport sport) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            session.update(sport);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while updating Sport: " + sport.getName(), e);
            throw new EmployeeException("Error while updating Sport: " + sport.getName(), e);
        }
    }

    @Override
    public Set<Employee> getEmployeesBySportId(int sportId) throws EmployeeException {
        try (Session session = HibernateConnection.getSession()) {
            Sport sport = session.get(Sport.class, sportId);
            return sport != null ? sport.getEmployees() : null;
        } catch (HibernateException e) {
            logger.error("Error while getting employees by sport ID: " + sportId, e);
            throw new EmployeeException("Error while getting employees by sport ID: " + sportId, e);
        }
    }
}
