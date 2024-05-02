package com.thecoderstv.springboottesting.service;

import com.thecoderstv.springboottesting.Exception.ResourceNotFoundException;
import com.thecoderstv.springboottesting.Model.Employee;
import com.thecoderstv.springboottesting.Repository.EmployeeRepository;
import com.thecoderstv.springboottesting.Service.Impl.EmployeeServiceImpl;

import static org.assertj.core.api.Assertions.*;

import static org.junit.Assert.*;


import org.assertj.core.api.BDDAssumptions;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) // with this annotation we tell mockito that we used annotation for mocking
public class EmployeeServiceTests {


    private Employee employee;
    // Using @Mock to mock the repository and further this mock objects get injects in another mock object which is present below
    @Mock
    private EmployeeRepository employeeRepository;

    // As our service class depends on repository so @InjectMock mock the repository object in service
    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    @BeforeEach
    public void setup() {
        //  employeeRepository = Mockito.mock(EmployeeRepository.class);
        //   employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("Shubham")
                .lastName("Nigam")
                .email("nigamshubhamxxx@gmail.com")
                .build();
    }

    // Junit testcase for save employee method
    @DisplayName("Junit testcase for save employee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given
        // mock service method internal repository calls
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
        when(employeeRepository.save(employee)).thenReturn(employee);

        // when
        Employee savedEmployee = employeeServiceImpl.saveEmployee(employee);

        // then
        assertThat(savedEmployee).isNotNull();
        assertEquals(employee, savedEmployee);
    }

    // Junit testcase for save employee method which throw a custom exception
    @DisplayName("Junit testcase for save employee method which throw a custom exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        // given - precondition setup
        // mock service method internal repository calls
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));


        // when - action or behaviour
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeServiceImpl.saveEmployee(employee);
        });

        // then
        // from here we are verifying, after throw statement control won't go to the next statement
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    // JUnit testcase to get all employees
    @DisplayName("JUnit testcase to get all employees")
    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() {
        // given - precondition setup
        // mock the internal method of getAllEmployee method of Service class
        when(employeeRepository.findAll()).thenReturn(List.of(employee, new Employee(2L, "Ram", "Singh", "ram@gmail.com")));

        // when - action or behaviour
        List<Employee> employeeList = employeeServiceImpl.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // JUnit testcase to get all employees with empty list
    @DisplayName("JUnit testcase to get all employees with empty list")
    @Test
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeeList() {
        // given - precondition setup
        // mock the internal method of getAllEmployee method of Service class
        when(employeeRepository.findAll()).thenReturn(List.of());

        // when - action or behaviour
        List<Employee> employeeList = employeeServiceImpl.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    // JUnit testcase to get employee by id
    @DisplayName("JUnit testcase to get employees by id")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given - precondition setup
        // mock the internal method of getAllEmployee method of Service class
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // when - action or behaviour
        Optional<Employee> employee = employeeServiceImpl.getEmployeeById(1L);

        // then - verify the output
        assertThat(employee).isNotNull();
    }

    // JUnit testcase to get employee by id negative case
    @DisplayName("JUnit testcase to get employee by id negative case")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenThrowsException() {
        // given - precondition setup
        // mock the internal method of getAllEmployee method of Service class
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // when - action or behaviour

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeServiceImpl.getEmployeeById(1L);
        });

        // then - verify the output

    }

    @DisplayName("JUnit testcase to update employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject() {
        // given - precondition setup
        // mock the internal call of updateEmployee Object
        when(employeeRepository.save(employee)).thenReturn(employee);
        employee.setEmail("update@gmail.com");

        // when - action or behaviour
        Employee updatedEmp = employeeServiceImpl.updateEmployee(employee);

        // then - verify the output
        assertThat(updatedEmp).isNotNull();
        assertThat(updatedEmp.getEmail()).isEqualTo("update@gmail.com");
    }

    @DisplayName("JUnit testcase to update employee negative case")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenThrowException() {
        Employee nonExistentEmployee = new Employee();
        nonExistentEmployee.setId(9999L); // Assuming ID 9999 does not exist in the database// Attempt to update the non-existent employee
        try {
            employeeServiceImpl.updateEmployee(nonExistentEmployee);
            // If the above line does not throw an exception, fail the test
            Assert.fail();
        } catch (ResourceNotFoundException e) {
            // Assert that the exception message contains the correct error message
            assertTrue(e.getMessage().contains("Employee with id 9999 not found"));
        }

    }


    @DisplayName("JUnit testcase to delete employee by id")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        // given - precondition setup
        doNothing().when(employeeRepository).deleteById(1L);

        // when - action or behaviour
        employeeServiceImpl.deleteEmployee(1L);

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @DisplayName("JUnit testcase to delete employee by id negative case")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenThrowException() {
        // Assuming ID 9999 does not exist in the database
        Long id = 9999L;

        // Attempt to delete the non-existent employee
        try {
            employeeServiceImpl.deleteEmployee(id);
            fail();
        } catch (ResourceNotFoundException e) {
            // Assert that the exception message contains the correct error message
            assertTrue(e.getMessage().contains("Employee with id " + id + " not found"));
        }

    }
}
