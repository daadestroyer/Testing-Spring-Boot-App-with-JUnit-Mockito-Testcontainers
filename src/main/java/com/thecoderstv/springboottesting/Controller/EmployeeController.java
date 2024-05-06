package com.thecoderstv.springboottesting.Controller;

import com.thecoderstv.springboottesting.Model.Employee;
import com.thecoderstv.springboottesting.Service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id){
        Optional<Employee> employeeById = employeeService.getEmployeeById(id);
        if(employeeById.isPresent()){
            return new ResponseEntity<>(employeeById, HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee Not Found",HttpStatus.NOT_FOUND);

    }

    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee){
        Employee savedEmp = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(savedEmp,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteEmpById(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
