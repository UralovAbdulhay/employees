package com.example.demo.controller;

import com.example.demo.controller.endpoint.DepartmentEndpoint;
import com.example.demo.payload.Result;
import com.example.demo.payload.requests.DepartmentRequest;
import com.example.demo.service.impl.DepartmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class DepartmentController implements DepartmentEndpoint {

    private final DepartmentServiceImpl departmentService;


    @Override
    public ResponseEntity<Result> save(DepartmentRequest request) {
        System.out.println(request);

        return mapper(departmentService.save(request));
    }

    @Override
    public ResponseEntity<Result> update(DepartmentRequest request) {
        return mapper(departmentService.update(request));
    }

    @Override
    public ResponseEntity<Result> get(@Min(1) @Valid @NotNull Long id) {
        return mapper(departmentService.findById(id));

    }

    @Override
    public ResponseEntity<Result> getAll(int page, int size) {
        return mapper(departmentService.findAll(page, size));
    }

    @Override
    public ResponseEntity<Result> delete(Long id) {
        return ResponseEntity.ok(Result.deleted(departmentService.delete(id)));
    }

    private ResponseEntity<Result> mapper(Object o) {
        return ResponseEntity.ok(Result.ok(o));
    }
}
