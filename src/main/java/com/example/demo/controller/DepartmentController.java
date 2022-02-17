package com.example.demo.controller;

import com.example.demo.base.BaseRequest;
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
    public ResponseEntity<Result> saveOrUpdate(DepartmentRequest request) {
        System.out.println(request.toString());
        System.out.println(request.getId());
        return mapper(departmentService.saveOrUpdate(request));
    }

    @Override
    public ResponseEntity<Result> get( @Min(1) @Valid @NotNull Long id) {
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
