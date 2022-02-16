package com.example.demo.service.impl;

import com.example.demo.entity.Department;
import com.example.demo.payload.requests.DepartmentRequest;
import com.example.demo.service.DepartmentService;

import java.util.Collection;
import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {


    @Override
    public Department saveOrUpdate(DepartmentRequest t) {
        return null;
    }

    @Override
    public List<Department> saveOrUpdate(Collection<DepartmentRequest> ts) {
        return null;
    }

    @Override
    public Department findById(Long id) {
        return null;
    }

    @Override
    public List<Department> findAll(int page, int size) {
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
