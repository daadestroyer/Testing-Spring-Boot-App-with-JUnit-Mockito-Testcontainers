package com.thecoderstv.springboottesting.repository;


import com.thecoderstv.springboottesting.Model.Employee;
import com.thecoderstv.springboottesting.Repository.EmployeeRepository;


import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.Optional;


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

    @DisplayName("Junit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindEmployeeById_thenReturnEmployeeObject() {
        // given - precondition setup
        Employee employee1 = Employee.builder()
                .firstName("Shubham")
                .lastName("Nigam")
                .email("nigamshubhamxxx@gmail.com")
                .build();

        Employee employee = employeeRepository.save(employee1);

        // when - action or behaviour
        Optional<Employee> savedEmployee = employeeRepository.findById(employee.getId());

        // then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
        Assert.assertEquals(employee.getId(), savedEmployee.get().getId());
    }

    @DisplayName("Junit test for get employee by email")
    @Test
    public void givenEmployeeObject_whenFindEmployeeByEmail_thenReturnEmployeeObject() {
        // given - precondition setup
        Employee employee1 = Employee.builder()
                .firstName("Shubham")
                .lastName("Nigam")
                .email("nigamshubhamxxx@gmail.com")
                .build();

        employeeRepository.save(employee1);

        // when - action or behaviour
        Employee empDB = employeeRepository.findByEmail(employee1.getEmail()).get();

        // then - verify the output
        Assertions.assertThat(empDB).isNotNull();
        Assert.assertEquals(employee1.getEmail(), empDB.getEmail());
    }

    @DisplayName("Junit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() {
        // given - precondition setup
        Employee employee1 = Employee.builder()
                .firstName("Shubham")
                .lastName("Nigam")
                .email("nigamshubhamxxx@gmail.com")
                .build();
        employeeRepository.save(employee1);

        // when - action or behaviour
        Employee savedEmp = employeeRepository.findById(employee1.getId()).get();
        savedEmp.setFirstName("Shubham-updated");
        savedEmp.setEmail("shubham@gmail.com");
        Employee updatedEmp = employeeRepository.save(savedEmp);

        // then - verify the output
        Assertions.assertThat(updatedEmp.getEmail()).isEqualTo("shubham@gmail.com");
        Assert.assertEquals(updatedEmp.getFirstName(), "Shubham-updated");
    }

    @DisplayName("Junit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDeleteEmployee_thenDeleteEmployeeObject() {
        // given - precondition setup
        Employee employee1 = Employee.builder()
                .firstName("Shubham")
                .lastName("Nigam")
                .email("nigamshubhamxxx@gmail.com")
                .build();
        employeeRepository.save(employee1);

        // when - action or behaviour
        employeeRepository.delete(employee1);
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee1.getId());

        // then - verify the output
        Assertions.assertThat(optionalEmployee).isEmpty();
    }

    @DisplayName("Junit test for custom query using JPQL with Index")
    @Test
    public void givenEmployeeFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        // given - precondition setup
        Employee employee1 = Employee.builder()
                .firstName("Shubham")
                .lastName("Nigam")
                .email("nigamshubhamxxx@gmail.com")
                .build();

        employeeRepository.save(employee1);
        String firstName = employee1.getFirstName();
        String lastName = employee1.getLastName();

        // when - action or behaviour
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        // then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
        
    }
}
