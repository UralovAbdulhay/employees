package com.example.demo.service.impl;

import com.example.demo.entity.Employee;
import com.example.demo.payload.requests.EmployeeRequest;
import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final Validator validator;


    @Override
    public Employee saveOrUpdate(EmployeeRequest t) {
        return null;
    }

    @Override
    public List<Employee> saveOrUpdate(Collection<EmployeeRequest> ts) {
        return null;
    }

    @Override
    public Employee findById(Long id) {
        return null;
    }

    @Override
    public List<Employee> findAll(int page, int size) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return false;
    }
}
