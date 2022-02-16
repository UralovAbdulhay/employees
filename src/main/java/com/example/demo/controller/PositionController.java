package com.example.demo.controller;

import com.example.demo.controller.endpoint.EmployeeEndpoint;
import com.example.demo.controller.endpoint.PositionEndpoint;
import com.example.demo.payload.Result;
import com.example.demo.payload.requests.PositionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PositionController implements PositionEndpoint {
    @Override
    public ResponseEntity<Result> saveOrUpdate(PositionRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Result> get(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Result> getAll(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<Result> delete(Long id) {
        return null;
    }
}
