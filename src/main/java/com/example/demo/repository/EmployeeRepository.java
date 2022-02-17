package com.example.demo.repository;

import com.example.demo.base.BaseRepository;
import com.example.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository("employee")

public interface EmployeeRepository extends BaseRepository<Employee> {

}
