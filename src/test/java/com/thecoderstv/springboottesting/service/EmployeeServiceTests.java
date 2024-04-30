package com.thecoderstv.springboottesting.service;

import com.thecoderstv.springboottesting.Model.Employee;
import com.thecoderstv.springboottesting.Repository.EmployeeRepository;
import com.thecoderstv.springboottesting.Service.EmployeeService;
import com.thecoderstv.springboottesting.Service.Impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
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
        Employee employee = Employee.builder()
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

        // stubbing service method internal repository calls
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
        when(employeeRepository.save(employee)).thenReturn(employee);

        // when
        Employee savedEmployee = employeeServiceImpl.saveEmployee(employee);

        // then
        assertThat(savedEmployee).isNotNull();
        assertEquals(employee,savedEmployee);
    }


}
