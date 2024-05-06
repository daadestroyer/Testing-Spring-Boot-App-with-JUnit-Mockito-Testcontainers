package com.thecoderstv.springboottesting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thecoderstv.springboottesting.Model.Employee;
import com.thecoderstv.springboottesting.Service.EmployeeService;
import com.thecoderstv.springboottesting.Service.Impl.EmployeeServiceImpl;
import lombok.Builder;

import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("JUnit testcase for Employee Controller to save employee")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given - precondition setup
        Employee employee = Employee.builder().firstName("Shubham").lastName("Nigam").email("shubham@gmail.com").build();
        when(employeeService.saveEmployee(ArgumentMatchers.any())).thenAnswer((invocation) -> invocation.getArgument(0));

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

    @DisplayName("JUnit testcase for Employee Controller to get all employee")
    @Test
    public void givenListOfEmployee_whenGetAllEmployee_thenReturnEmployeeList() throws Exception {
        // given - precondition setup
        List<Employee> employeeList = List.of(
                Employee.builder().firstName("Shubham").lastName("Nigam").email("shubham@gmail.com").build(), Employee.builder().firstName("Ram").lastName("Singh").email("ram@gmail.com").build()
        );
        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        // when - action or behaviour
        ResultActions response = mockMvc.perform(get("/api/employees/get-all-employee"));

        // then - verify the output
        response
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(employeeList.size())));
    }

    @DisplayName("JUnit testcase for Employee Controller to get employee by id [positive scenario - valid employee id]")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        // given - precondition setup
        long empId = 1L;
        Employee employee = Employee.builder().firstName("Shubham").lastName("Nigam").email("shubham@gmail.com").build();
        when(employeeService.getEmployeeById(empId)).thenReturn(Optional.of(employee));

        // when - action or behaviour
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", empId));

        // then - verify the output
        response
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @DisplayName("JUnit testcase for Employee Controller to get employee by id [negative scenario - invalid employee id")
    @Test
    public void given_when_then() throws Exception {
        // given - precondition setup
        long empId = 12L;
        Employee employee = Employee.builder().firstName("Shubham").lastName("Nigam").email("shubham@gmail.com").build();
        when(employeeService.getEmployeeById(empId)).thenReturn(Optional.empty());

        // when - action or behaviour
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", empId));

        // then - verify the output
        response
                .andExpect(status().isNotFound())
                .andDo(print());

    }

}
