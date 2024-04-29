package com.thecoderstv.springboottesting.Repository;

import com.thecoderstv.springboottesting.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findByEmail(String email);

    // define custom query using JPQL(Java Persistance Query Language) with index name
    @Query("select e from Employee e  where e.firstName = :firstName and e.lastName = :lastName")
    Employee findByJPQL(String firstName, String lastName);

}
