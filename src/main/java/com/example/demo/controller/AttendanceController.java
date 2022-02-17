package com.example.demo.controller;

import com.example.demo.controller.endpoint.AttendanceEndpoint;
import com.example.demo.payload.Result;
import com.example.demo.payload.requests.AttendanceRequest;
import com.example.demo.service.impl.AttendanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AttendanceController implements AttendanceEndpoint {

    private final AttendanceServiceImpl attendanceService;

    @Override
    public ResponseEntity<Result> saveOrUpdate(AttendanceRequest request) {
        System.out.println(request.toString());
        System.out.println(request.getId());
        return mapper(attendanceService.saveOrUpdate(request));
    }

    @Override
    public ResponseEntity<Result> get(Long id) {
        return mapper(attendanceService.findById(id));

    }

    @Override
    public ResponseEntity<Result> getAll(int page, int size) {
        return mapper(attendanceService.findAll(page, size));
    }

    @Override
    public ResponseEntity<Result> delete(Long id) {
        return ResponseEntity.ok(Result.deleted(attendanceService.delete(id)));
    }

    private ResponseEntity<Result> mapper(Object o) {
        return ResponseEntity.ok(Result.ok(o));
    }
}
