package com.example.demo.repository;

import com.example.demo.base.BaseRepository;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;



public interface DepartmentRepository extends BaseRepository<Department> {

}
