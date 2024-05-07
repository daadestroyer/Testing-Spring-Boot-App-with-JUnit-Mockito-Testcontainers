package com.thecoderstv.springboottesting.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thecoderstv.springboottesting.Model.Employee;
import com.thecoderstv.springboottesting.Repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc; // Injecting MockMvc class to perform different Http Request using perform() method
    @Autowired
    private EmployeeRepository employeeRepository; // Injecting EmployeeRepository to use its method's to perform different operations on database

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
    }

    @DisplayName("JUnit integration testcase for Employee Controller to save employee")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given - precondition setup
        Employee employee = Employee.builder().firstName("Shubham").lastName("Nigam").email("shubham@gmail.com").build();

        // we need to remove mocking statement as this is the integration testing
//        when(employeeService.saveEmployee(ArgumentMatchers.any())).thenAnswer((invocation) -> invocation.getArgument(0));

        // when - action or behaviour
        ResultActions response = mockMvc.perform(post("/api/employees/create-employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));


        // then - verify the output
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Shubham"))
                .andExpect(jsonPath("$.lastName").value("Nigam"))
                .andExpect(jsonPath("$.email").value("shubham@gmail.com"));
    }


    @DisplayName("JUnit integration testcase for Employee Controller to get all employee")
    @Test
    public void givenListOfEmployee_whenGetAllEmployee_thenReturnEmployeeList() throws Exception {
        // given - precondition setup
        List<Employee> employeeList = List.of(
                Employee.builder().firstName("Shubham").lastName("Nigam").email("shubham@gmail.com").build(), Employee.builder().firstName("Ram").lastName("Singh").email("ram@gmail.com").build()
        );

        employeeRepository.saveAll(employeeList);
        // when - action or behaviour
        ResultActions response = mockMvc.perform(get("/api/employees/get-all-employee"));

        // then - verify the output
        response
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(employeeList.size())));
    }

    @DisplayName("JUnit integration testcase for Employee Controller to get employee by id [positive scenario - valid employee id]")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        // given - precondition setup

        Employee employee = Employee.builder().firstName("Shubham").lastName("Nigam").email("shubham@gmail.com").build();
        employeeRepository.save(employee);

        // when - action or behaviour
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        // then - verify the output
        response
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @DisplayName("JUnit integration testcase for Employee Controller to get employee by id [negative scenario - invalid employee id")
    @Test
    public void given_when_then() throws Exception {
        // given - precondition setup
        long empId = 1L;
        Employee employee = Employee.builder().firstName("Shubham").lastName("Nigam").email("shubham@gmail.com").build();
        employeeRepository.save(employee);

        // when - action or behaviour
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", empId));

        // then - verify the output
        response
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @DisplayName("JUnit integration testcase for update employee by id [possitive scenario - valid id]")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() throws Exception {
        // given - precondition setup
        // employee which is already in db
        Employee employee = Employee.builder().firstName("Ram").lastName("Nigam").email("ram@gmail.com").build();

        employeeRepository.save(employee);

        // updated employee
        employee.setFirstName("Ramupdate");
        employee.setLastName("Nigamupdate");
        employee.setEmail("ramupdate@gmail.com");


        // then - verify the output
        ResultActions response = mockMvc.perform(put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    @DisplayName("JUnit integration testcase for update employee by id [negative scenario - invalid id]")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {
        // given - precondition setup
        // employee which is already in db
        Long empId = 1L;
        Employee savedEmp = Employee.builder().firstName("Ram").lastName("Nigam").email("ram@gmail.com").build();
        employeeRepository.save(savedEmp);

        // updated employee
        Employee updatedEmp = Employee.builder().id(121L).firstName("Ramnew").lastName("Nigamnew").email("ram@gmail.com").build();

        // then - verify the output
        ResultActions response = mockMvc.perform(put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmp)));

        response
                .andExpect(status().isNotFound());

    }


}
