package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceImpl;
import com.example.demo.entity.Department;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.payload.requests.DepartmentRequest;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.service.DepartmentService;
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
public class DepartmentServiceImpl extends BaseServiceImpl<Department, DepartmentRequest> implements DepartmentService {


    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(Validator validator, DepartmentRepository departmentRepository) {
        super(validator, departmentRepository);
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department saveOrUpdate(DepartmentRequest request) {
        return validate(new Department(), request);
    }

    @Override
    public List<Department> saveOrUpdate(Collection<DepartmentRequest> requests) {
        return requests.stream().map(this::saveOrUpdate).collect(Collectors.toList());
    }

    @Override
    public Department findById(@Valid @NotNull @Min(1) Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> ResourceNotFound.get("Department", "id", id));
    }

    @Override
    public List<Department> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return departmentRepository.findAll(pageable).getContent();
    }

    @Override
    public boolean delete(Long id) {
        departmentRepository.deleteById(id);
        return !existById(id);
    }

    @Override
    public boolean existById(Long id) {
        return departmentRepository.existsById(id);
    }
}
