package com.thecoderstv.springboottesting.repository;


import com.thecoderstv.springboottesting.Model.Employee;
import com.thecoderstv.springboottesting.Repository.EmployeeRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;



// this annotation loads only repository layer components
@DataJpaTest
public class EmployeeRepositoryTest {


    @Autowired
    EmployeeRepository employeeRepository;

    // Junit testcase for save employee operation
    // following given_when_then condition
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given  - precondition or setup or sample data
        Employee employee = Employee.builder()
                .firstName("Shubham")
                .lastName("Nigam")
                .email("nigamshubhamxxx@gmail.com")
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        Assertions.assertThat(savedEmployee.getId()).isEqualTo(employee.getId());
    }
}
