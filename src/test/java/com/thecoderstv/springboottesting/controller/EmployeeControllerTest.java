package com.thecoderstv.springboottesting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thecoderstv.springboottesting.Model.Employee;
import com.thecoderstv.springboottesting.Service.EmployeeService;
import com.thecoderstv.springboottesting.Service.Impl.EmployeeServiceImpl;
import org.hamcrest.CoreMatchers;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("JUnit Controller test to save employee")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given - precondition setup
        Employee employee = Employee.builder().firstName("Shubham").lastName("Nigam").email("shubham@gmail.com").build();
        when(employeeService.saveEmployee(ArgumentMatchers.any())).thenAnswer((invocation)->invocation.getArgument(0));

        // when - action or behaviour
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees/create-employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));


        // then - verify the output
        response.andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Shubham"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Nigam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("shubham@gmail.com"));
    }

}
