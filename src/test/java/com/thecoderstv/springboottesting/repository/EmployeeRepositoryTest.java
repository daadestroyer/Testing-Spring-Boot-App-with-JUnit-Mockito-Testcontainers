package com.thecoderstv.springboottesting.repository;


import com.thecoderstv.springboottesting.Model.Employee;
import com.thecoderstv.springboottesting.Repository.EmployeeRepository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;


// this annotation loads only repository layer components
@DataJpaTest
public class EmployeeRepositoryTest {


    @Autowired
    EmployeeRepository employeeRepository;

    // Junit testcase for save employee operation
    // following given_when_then condition

    @DisplayName("Junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given  - precondition or setup or sample data
        Employee employee = Employee.builder()
                .firstName("Shubham")
                .lastName("Nigam")
                .email("nigamshubhamxxx@gmail.com")
                .build();

        // when - action or behaviour
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        Assertions.assertThat(savedEmployee.getId()).isEqualTo(employee.getId());
    }

    @DisplayName("Junit test for get all employee operation")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList() {
        // given - precondition setup
        Employee employee1 = Employee.builder()
                .firstName("Shubham")
                .lastName("Nigam")
                .email("nigamshubhamxxx@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Ram")
                .lastName("Nigam")
                .email("ramnigam@gmail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // when - action or behaviour
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }
}
