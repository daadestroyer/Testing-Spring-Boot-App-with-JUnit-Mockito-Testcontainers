package com.thecoderstv.springboottesting.Controller;

import com.thecoderstv.springboottesting.Model.Employee;
import com.thecoderstv.springboottesting.Service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping("/create-employee")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }
    @GetMapping("/get-all-employee")
    public List<Employee> getAllEmployee(){
        return employeeService.getAllEmployees();
    }

}
