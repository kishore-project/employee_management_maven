package com.ideas2it.employee.service;

import java.util.List;
import java.util.Set;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ideas2it.exceptions.EmployeeException;
import com.ideas2it.department.service.DepartmentService;
import com.ideas2it.department.service.DepartmentServiceImpl;
import com.ideas2it.employee.dao.EmployeeRepository;
import com.ideas2it.employee.dao.EmployeeRepositoryImpl;
import com.ideas2it.model.Address;
import com.ideas2it.model.Department;
import com.ideas2it.model.Employee;
import com.ideas2it.model.Sport;
import com.ideas2it.sports.service.SportService;
import com.ideas2it.sports.service.SportServiceImpl;

/**
 * <p>
 * Implement if EmployeeService interface to handle employee-related operations.
 * </p>
 * @author  Kishore 
 * @version 1.0 
 */
public class EmployeeServiceImpl implements EmployeeService{

    private static final Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);
    private EmployeeRepository employeeRepository;
    private DepartmentService departmentService = new DepartmentServiceImpl();
    private SportService sportService = new SportServiceImpl();

    public EmployeeServiceImpl() {
        employeeRepository = new EmployeeRepositoryImpl();
    }

    @Override
    public void addEmployee(int id, String name, LocalDate dob, String emailId,
                            int deptId, Address address) throws IllegalArgumentException, EmployeeException {
        Department department = departmentService.getDepartmentById(deptId);
        if (department == null) {
            logger.info("Department not found" +deptId);
            throw new IllegalArgumentException("Department not found" +deptId);

        }

        Employee employee = new Employee(id, name, dob, department, emailId, address); //ID will be set in the repository
        employeeRepository.addEmployee(employee);
    }

    @Override
    public void removeEmployee(int id) throws IllegalArgumentException, EmployeeException {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee != null) {
            employeeRepository.deleteEmployee(id);
        } else {
            logger.info("Employee not found" +id);
            throw new IllegalArgumentException("Employee not found" +id);
        }
    }
    
    @Override
    public List<Employee> getAllEmployees() throws EmployeeException {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public Employee getEmployeeById(int id) throws EmployeeException {
        return employeeRepository.findEmployeeById(id);
    }

    @Override
    public void updateEmployee(int id, String name, LocalDate dob, String emailId, int deptId, Address updatedAddress) throws IllegalArgumentException, EmployeeException {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee != null) {
            employee.setName(name);
            employee.setDob(dob);
            employee.setEmailId(emailId);

            // Get the existing address from the database
            Address existingAddress = employee.getAddress();
            if (existingAddress != null) {
                // Update the existing address details
                existingAddress.setStreet(updatedAddress.getStreet());
                existingAddress.setCity(updatedAddress.getCity());
                existingAddress.setState(updatedAddress.getState());
                existingAddress.setZip(updatedAddress.getZip());
            } else {
                // Set the new address if there's no existing one
                employee.setAddress(updatedAddress);
            }

            Department department = departmentService.getDepartmentById(deptId);
            if (department != null) {
                employee.setDepartment(department);
            } else {
                throw new IllegalArgumentException("Department not found" + deptId);
            }
            employeeRepository.updateEmployee(employee);
        } else {
            throw new IllegalArgumentException("Employee not found" + id);
        }
    }

    @Override
    public Department getDepartmentById(int id) throws EmployeeException {
        return departmentService.getDepartmentById(id);
    }
    
    @Override
    public List<Department>getAllDepartment() throws EmployeeException {
       return departmentService.getAllDepartments();
    }

    @Override
    public void addSportToEmployee(int employeeId, int sportId) throws EmployeeException {
        Employee employee = getEmployeeById(employeeId);
        Sport sport = sportService.getSportById(sportId);

        if (employee != null && sport != null) {
            employeeRepository.addSportToEmployee(employeeId, sportId);
        } else {
            logger.info("Employee or Sport not found");
            throw new IllegalArgumentException("Employee or Sport not found");
        }
    }


    @Override
    public void removeSportFromEmployee(int employeeId, int sportId) throws EmployeeException {
        Employee employee = getEmployeeById(employeeId);
        Sport sport = sportService.getSportById(sportId);

        if (employee != null && sport != null) {
            employeeRepository.removeSportFromEmployee(employeeId, sportId);
        } else {
            throw new IllegalArgumentException("Employee or Sport not found");
        }
    }
    
    @Override
    public Set<Sport> getAllSports() throws EmployeeException {
        return sportService.getAllSports();
    }
}

