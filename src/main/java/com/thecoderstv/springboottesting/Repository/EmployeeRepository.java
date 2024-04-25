package com.thecoderstv.springboottesting.Repository;

import com.thecoderstv.springboottesting.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
