package com.thecoderstv.springboottesting.Service.Impl;

import com.thecoderstv.springboottesting.Exception.ResourceNotFoundException;
import com.thecoderstv.springboottesting.Model.Employee;
import com.thecoderstv.springboottesting.Repository.EmployeeRepository;
import com.thecoderstv.springboottesting.Service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(employee.getEmail());
        if (employeeOptional.isPresent()) {
            throw new ResourceNotFoundException("Employee already exist with given email : " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> listOfEmp = employeeRepository.findAll();
        return listOfEmp;
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            throw new ResourceNotFoundException("Employee already exist with given id : " + id);
        }
        return employee;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);

    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
