package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceImpl;
import com.example.demo.entity.Employee;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.payload.requests.EmployeeRequest;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class EmployeeServiceImpl extends BaseServiceImpl<Employee, EmployeeRequest> implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(Validator validator, EmployeeRepository employeeRepository) {
        super(validator, employeeRepository);
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveOrUpdate(EmployeeRequest request) {
        return validate(new Employee(), request);
    }

    @Override
    public List<Employee> saveOrUpdate(Collection<EmployeeRequest> requests) {
        return requests.stream().map(this::saveOrUpdate).collect(Collectors.toList());
    }

    @Override
    public Employee findById(@Valid @NotNull @Min(1) Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> ResourceNotFound.get("Employee", "id", id));
    }

    @Override
    public List<Employee> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable).getContent();
    }

    @Override
    public boolean delete(Long id) {
        employeeRepository.deleteById(id);
        return !existById(id);
    }

    @Override
    public boolean existById(Long id) {
        return employeeRepository.existsById(id);
    }

}
