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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void setup(){
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


}
