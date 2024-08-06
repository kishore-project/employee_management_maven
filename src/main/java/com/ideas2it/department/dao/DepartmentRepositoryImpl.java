package com.ideas2it.department.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.department.dao.DepartmentRepository;
import com.ideas2it.exceptions.DataBaseException;
import com.ideas2it.model.Department;
import com.ideas2it.model.Employee;
import com.ideas2it.utilities.HibernateConnection;


/**
 * Repository for managing department, including adding, removing, updating and 
 * retrieving employees by department id.
 * @author  Kishore 
 * @version 1.0 
 */
public class DepartmentRepositoryImpl implements DepartmentRepository {
    private static final Logger logger = LogManager.getLogger(DepartmentRepositoryImpl.class);

    /**
     * Adds a new department to the database
     *
     * @param department - The department to be added.
     */
    @Override
    public void addDepartment(Department department) throws DataBaseException {
        Transaction transaction = null;
        try (Session session =  HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            session.save(department);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while adding Department: " + department.getName(), e);
            throw new DataBaseException("Error while adding Department: " + department.getName(), e);
        }
    }

   /**
    *Deletes an department from the database by ID.
    *
    *@param id - departemnt to delete.
    */
    @Override
    public void deleteDepartment(int id) throws DataBaseException {
        Transaction transaction = null;
        try (Session session =  HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            Department department = session.get(Department.class, id);
            if (department != null) {
                department.setIsDeleted(true);
                session.update(department);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while deleting Department: " + id, e);
            throw new DataBaseException("Error while deleting Department: " + id, e);
        }
    }

    /**
     * Retrieves all department from the database.
     *
     * @return a list of all department.
     */
    @Override
    public List<Department> getAllDepartments() throws DataBaseException {
        try (Session session =  HibernateConnection.getSession()) {
            return session.createQuery("FROM Department WHERE isDeleted = false", Department.class).list();
        } catch (HibernateException e) {
            logger.debug("Error while getting all Departments", e);
            throw new DataBaseException("Error while getting all Departments", e);
        }
    }

    /**
     * Finds an department by ID.
     *
     * @param id - department to find.
     * @return The department if found, null otherwise.
     */
    @Override
    public Department findDepartmentById(int id) throws DataBaseException {
        try (Session session =  HibernateConnection.getSession()) {
            return session.get(Department.class, id);
        } catch (HibernateException e) {
            logger.error("Error while getting Department by ID: " + id, e);
            throw new DataBaseException("Error while getting Department by ID: " + id, e);
        }
    }

    /**
     * Updates an existing department in the database.
     *
     * @param department - department with update details.
     */
    @Override
    public void updateDepartment(Department department) throws DataBaseException {
        Transaction transaction = null;
        try (Session session =  HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            session.update(department);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while updating Department: " + department.getName(), e);
            throw new DataBaseException("Error while updating Department: " + department.getName(), e);
        }
    }

     /**
     * Finds employees by department ID.
     *
     * @param departmentId - ID of the department.
     * @return A list of employees in the specified department.
     */
    @Override
    public List<Employee> getEmployeesByDepartmentId(int departmentId) throws DataBaseException {
        try (Session session =  HibernateConnection.getSession()) {
            Department department = session.get(Department.class, departmentId);
            if (department != null) {
                return new ArrayList<>(department.getEmployees());
            } else {
                return new ArrayList<>();
            }
        } catch (HibernateException e) {
            logger.error("Error while getting Employees by Department ID: " + departmentId, e);
            throw new DataBaseException("Error while getting Employees by Department ID: " + departmentId, e);
        }
    }

}