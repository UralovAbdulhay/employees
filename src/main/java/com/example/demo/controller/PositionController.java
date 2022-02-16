package com.example.demo.controller;

import com.example.demo.controller.endpoint.EmployeeEndpoint;
import com.example.demo.controller.endpoint.PositionEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PositionController implements PositionEndpoint {
    @Override
    public ResponseEntity<?> saveOrUpdate(Object request) {
        return null;
    }

    @Override
    public ResponseEntity<?> get(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAll(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return null;
    }
}
