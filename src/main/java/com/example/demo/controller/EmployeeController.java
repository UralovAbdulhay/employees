package com.example.demo.controller;

import com.example.demo.controller.endpoint.EmployeeEndpoint;
import com.example.demo.payload.Result;
import com.example.demo.payload.requests.EmployeeRequest;
import com.example.demo.service.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeEndpoint {
    private final EmployeeServiceImpl employeeService;


    @Override
    public ResponseEntity<Result> save(EmployeeRequest request) {
        return mapper(employeeService.save(request));
    }

    @Override
    public ResponseEntity<Result> update(EmployeeRequest request) {
        return mapper(employeeService.update(request));
    }

    @Override
    public ResponseEntity<Result> get(Long id) {
        return mapper(employeeService.findById(id));

    }

    @Override
    public ResponseEntity<Result> getAll(int page, int size) {
        return mapper(employeeService.findAll(page, size));
    }

    @Override
    public ResponseEntity<Result> delete(Long id) {
        return ResponseEntity.ok(Result.deleted(employeeService.delete(id)));
    }

    private ResponseEntity<Result> mapper(Object o) {
        return ResponseEntity.ok(Result.ok(o));
    }
}
