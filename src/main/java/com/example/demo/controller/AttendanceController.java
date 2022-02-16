package com.example.demo.controller;

import com.example.demo.controller.endpoint.AttendanceEndpoint;
import com.example.demo.payload.Result;
import com.example.demo.payload.requests.AttendanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AttendanceController implements AttendanceEndpoint {
    @Override
    public ResponseEntity<Result> saveOrUpdate(AttendanceRequest request) {
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
