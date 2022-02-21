package com.example.demo.controller;

import com.example.demo.controller.endpoint.AttendanceEndpoint;
import com.example.demo.payload.Result;
import com.example.demo.payload.requests.AttendanceRequest;
import com.example.demo.service.impl.AttendanceServiceImpl;
import com.example.demo.service.impl.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class AttendanceController implements AttendanceEndpoint {

    private final AttendanceServiceImpl attendanceService;
    private final FileService fileService;


    @Override
    public ResponseEntity<Result> save(AttendanceRequest request) {
        return mapToResponse(attendanceService.save(request));
    }

    @Override
    public ResponseEntity<Result> update(AttendanceRequest request) {
        return mapToResponse(attendanceService.update(request));
    }

    @Override
    public ResponseEntity<Result> get(Long id) {
        return mapToResponse(attendanceService.findById(id));

    }

    @Override
    public ResponseEntity<Result> getAll(int page, int size) {
        return mapToResponse(attendanceService.findAll(page, size));
    }

    @Override
    public ResponseEntity<Result> delete(Long id) {
        return ResponseEntity.ok(Result.deleted(attendanceService.delete(id)));
    }

    @Override
    public ResponseEntity uploadFile(MultipartFile file) {
        return mapToResponse(attendanceService.importFromExcel(file, new AttendanceRequest()));
    }

    @Override
    public ResponseEntity<ByteArrayResource> downloadExcelFile() {
        return fileService.downloadFileFromServer(attendanceService.exportAll());
    }

    private ResponseEntity<Result> mapToResponse(Object o) {
        return ResponseEntity.ok(Result.ok(o));
    }
}
