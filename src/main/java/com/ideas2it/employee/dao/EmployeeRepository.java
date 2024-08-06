package com.ideas2it.employee.dao;

import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.exceptions.DataBaseException;
import com.ideas2it.department.dao.DepartmentRepository;
import com.ideas2it.employee.dao.EmployeeRepository;
import com.ideas2it.employee.service.EmployeeService;
import com.ideas2it.model.Department;
import com.ideas2it.model.Employee;
import com.ideas2it.model.Sport;
import com.ideas2it.sports.dao.SportRepository;

/**
 * Repository for managing employees, including adding, removing, updating and retrieving employees.
 * @author  Kishore 
 * @version 1.0 
 */
public interface EmployeeRepository {
    
    /**
     * Adds a new employee to the database.
     *
     * @param employee - The employee to be added.
     */
    public void addEmployee(Employee employee) throws DataBaseException;

   /**
    * Deletes an employee from the database by ID.
    *
    * @param id - employee to delete.
    */
    public void deleteEmployee(int id) throws DataBaseException;

    /**
     * Retrieves all employees from the database.
     *
     * @return alist of all employees.
     */
    public List<Employee> getAllEmployees() throws DataBaseException;

    /**
     * Finds an employee by ID.
     *
     * @param id - employee to find.
     * @return The employee if found, null otherwise.
     */
    public Employee findEmployeeById(int id) throws DataBaseException;

    /**
     * Updates an existing employee in the database.
     *
     * @param employee - emplyee with update details.
     */
    public void updateEmployee(Employee employee) throws DataBaseException;
  
    /**
     * Adds a sport to an employee.
     *
     * @param employeeId - Id of employee.
     * @param sportId - Id of sport.
     */    
    public void addSportToEmployee(int employeeId, int sportId) throws DataBaseException;

    /**
     * remove a sport to an employee.
     *
     * @param employeeId - Id of employee.
     * @param sportId - Id of sport.
     */
    public void removeSportFromEmployee(int employeeId, int sportId) throws DataBaseException;
              
}
       

    
