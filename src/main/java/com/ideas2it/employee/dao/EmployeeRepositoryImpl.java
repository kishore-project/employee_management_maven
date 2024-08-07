package com.ideas2it.employee.dao;

import java.util.List;

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
 * Repository for managing employees, including adding, removing, updating and retrieving employees.
 * @author  Kishore 
 * @version 1.0 
 */
public class EmployeeRepositoryImpl implements EmployeeRepository {
        private static final Logger logger = LogManager.getLogger(EmployeeRepository.class);

    @Override
    public void addEmployee(Employee employee) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while adding Employee: " + employee.getName(), e);
            throw new EmployeeException("Error while adding Employee: " + employee.getName(), e);
        }
    }

    @Override
    public void deleteEmployee(int id) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                employee.setIsActive(false);
                session.update(employee);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while deleting Employee: " + id, e);
            throw new EmployeeException("Error while deleting Employee: " + id, e);
        }
    }

    @Override
    public List<Employee> getAllEmployees() throws EmployeeException {
        try (Session session = HibernateConnection.getSession()) {
            Query<Employee> query = session.createQuery("From Employee where isActive = true", Employee.class);
            return query.list();
        } catch (HibernateException e) {
            logger.info("Error while getting all employees", e);
            throw new EmployeeException("Error while getting all employees", e);
        }
    }

    @Override
    public Employee findEmployeeById(int id) throws EmployeeException {
        try (Session session = HibernateConnection.getSession()) {
            String hql = "FROM Employee e LEFT JOIN FETCH e.department WHERE e.id = :id";
            Query<Employee> query = session.createQuery(hql, Employee.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (EmployeeException e) {
            logger.error("Error while getting Employee by ID: " + id, e);
            throw new EmployeeException("Error while getting Employee by ID: " + id, e);
        }
    }

    @Override
    public void updateEmployee(Employee employee) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while updating Employee: " + employee.getName(), e);
            throw new EmployeeException("Error while updating Employee: " + employee.getName(), e);
        }
    }

    @Override    
    public void addSportToEmployee(int employeeId, int sportId) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, employeeId);
            Sport sport = session.get(Sport.class, sportId);
            if (employee != null && sport != null) {
                employee.getSports().add(sport);
                sport.getEmployees().add(employee);
                session.saveOrUpdate(employee);
                session.saveOrUpdate(sport);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while adding Sport to Employee: " + employeeId, e);
            throw new EmployeeException("Error while adding Sport to Employee: " + employeeId, e);
        }
    }

    @Override
    public void removeSportFromEmployee(int employeeId, int sportId) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, employeeId);
            Sport sport = session.get(Sport.class, sportId);
            if (employee != null && sport != null) {
                employee.getSports().remove(sport);
                session.saveOrUpdate(employee);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) { 
                transaction.rollback();
            }
            logger.error("Error while removing Sport from Employee: " + employeeId, e);
            throw new EmployeeException("Error while removing Sport from Employee: " + employeeId, e);
        }
    }
              
}
       

    
