package com.thecoderstv.springboottesting.service;

import com.thecoderstv.springboottesting.Model.Employee;
import com.thecoderstv.springboottesting.Repository.EmployeeRepository;
import com.thecoderstv.springboottesting.Service.EmployeeService;
import com.thecoderstv.springboottesting.Service.Impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;


public class EmployeeServiceTests {


    // we are going to test employee Service class
    private EmployeeService employeeService;

    // employee service class depends on employee repository
    private EmployeeRepository employeeRepository;


    @BeforeEach
    public void setup() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    // Junit testcase for save employee method
    @DisplayName("Junit testcase for save employee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Shubham")
                .lastName("Nigam")
                .email("nigamshubhamxxx@gmail.com")
                .build();
        // stubbing service method internal repository calls
        Mockito.when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        // when
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then
        Assertions.assertThat(savedEmployee).isNotNull();
        Assert.assertEquals(employee,savedEmployee);
    }


}
