package com.ideas2it.department.service;

import java.sql.SQLException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ideas2it.exceptions.EmployeeException;
import com.ideas2it.department.dao.DepartmentRepository;
import com.ideas2it.department.dao.DepartmentRepositoryImpl;
import com.ideas2it.department.service.DepartmentService;
import com.ideas2it.employee.service.EmployeeService;
import com.ideas2it.model.Department;
import com.ideas2it.model.Employee;

/**
 * <p>
 * Implement if DepartmentService interface to handle department-related operations.
 * </p>
 * @author  Kishore 
 * @version 1.0 
 */
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger logger = LogManager.getLogger(DepartmentServiceImpl.class);
    private DepartmentRepository departmentRepository;

    public DepartmentServiceImpl() {
        departmentRepository = new DepartmentRepositoryImpl();
    }

    @Override
    public void addDepartment(int id, String name) throws EmployeeException {
        Department department = new Department(id, name);
        departmentRepository.addDepartment(department);
    }

    @Override
    public void removeDepartment(int id) throws IllegalArgumentException, EmployeeException {
        Department department = departmentRepository.findDepartmentById(id);
        if (department != null) {
            departmentRepository.deleteDepartment(id);
        } else {
            logger.error("Department not found" +id);
            throw new IllegalArgumentException("Department not found" +id);
        }
    }

    @Override 
    public List<Department> getAllDepartments() throws EmployeeException {
        return departmentRepository.getAllDepartments();
    }

    @Override
    public Department getDepartmentById(int id) throws EmployeeException {
        return departmentRepository.findDepartmentById(id);
    }

    @Override
    public void updateDepartment(int id, String name) throws IllegalArgumentException, EmployeeException {
        Department department = departmentRepository.findDepartmentById(id);
        if (department != null) {
            department.setName(name);
            departmentRepository.updateDepartment(department);
        } else {
            logger.error("Department not found" +id);
            throw new IllegalArgumentException("Department not found"+id);
        }
    }

    @Override
    public List<Employee> getEmployeesByDepartmentId(int id) throws EmployeeException {
        return departmentRepository.getEmployeesByDepartmentId(id);
    }

}
