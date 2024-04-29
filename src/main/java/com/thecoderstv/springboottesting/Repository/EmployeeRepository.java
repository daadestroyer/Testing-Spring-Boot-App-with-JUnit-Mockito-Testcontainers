package com.thecoderstv.springboottesting.Repository;

import com.thecoderstv.springboottesting.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findByEmail(String email);

    // define custom query using JPQL(Java Persistance Query Language) with index name
    @Query("select e from Employee e  where e.firstName = :firstName and e.lastName = :lastName")
    Employee findByJPQL(String firstName, String lastName);

    // defining native query which is a normal SQL query
    @Query(value="select * from employees e where e.first_name = ?1 and e.last_name = ?2",nativeQuery = true)
    Employee findByNativeQuery(String firstName,String lastName);
}
