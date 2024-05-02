package com.thecoderstv.springboottesting.Service;

import com.thecoderstv.springboottesting.Model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long id);

    Employee updateEmployee(Employee employee);

}
