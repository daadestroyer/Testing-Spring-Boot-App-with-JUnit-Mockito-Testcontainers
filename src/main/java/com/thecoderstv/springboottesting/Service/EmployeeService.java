package com.thecoderstv.springboottesting.Service;

import com.thecoderstv.springboottesting.Model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();

}
