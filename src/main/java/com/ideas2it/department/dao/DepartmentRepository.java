package com.ideas2it.department.dao;

import java.util.List;

import com.ideas2it.exceptions.EmployeeException;
import com.ideas2it.model.Department;
import com.ideas2it.model.Employee;

/**
 *<p>
 * Interface for departmentservice  to handle  department-related operation.
 *</p>
 *
 * @author  Kishore 
 * @version 1.0 
 */
public interface DepartmentRepository {

    /**
     * Adds a new department to the database.
     *
     * @param department - The department to be added.
     */
    public void addDepartment(Department department) throws EmployeeException;

    /**
     *Deletes an department from the database by ID.
     *
     *@param id - departemnt to delete.
     */   
    public void deleteDepartment(int id) throws EmployeeException;

    /**
     * Retrieves all department from the database.
     *
     * @return a list of all department.
     */
    public List<Department> getAllDepartments()throws EmployeeException;

    /**
     * Finds an department by ID.
     *
     * @param id - department to find.
     * @return The department if found, null otherwise.
     */
    public Department findDepartmentById(int id) throws EmployeeException;

    /**
     * Updates an existing department in the database.
     * @param department - department with update details.
     */
    public void updateDepartment(Department department) throws EmployeeException;

    /**
     * Finds employees by department ID using a Left JOIN.
     * @param departmentId - ID of the department.
     * @return A list of employees in the specified department.
     */
    public List<Employee> getEmployeesByDepartmentId(int departmentId) throws EmployeeException;

}



