package com.example.demo.controller;

import com.example.demo.controller.endpoint.EmployeeEndpoint;
import com.example.demo.file.fileInStorage.InStorageFileService;
import com.example.demo.payload.Result;
import com.example.demo.payload.requests.EmployeeRequest;
import com.example.demo.service.impl.EmployeeServiceImpl;
import com.example.demo.service.impl.ExportFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeEndpoint {
    private final EmployeeServiceImpl employeeService;
    private final ExportFileService exportFileService;
    private final InStorageFileService inStorageFileService;


    @Override
    public ResponseEntity<Result> save(EmployeeRequest request) {
        return mapToResponse(employeeService.save(request));
    }

    @Override
    public ResponseEntity<Result> update(EmployeeRequest request) {
        return mapToResponse(employeeService.update(request));
    }

    @Override
    public ResponseEntity<Result> get(Long id) {
        return mapToResponse(employeeService.findById(id));

    }

    @Override
    public ResponseEntity<Result> getAll(int page, int size) {
        return mapToResponse(employeeService.findAll(page, size));
    }

    @Override
    public ResponseEntity<Result> delete(Long id) {
        return ResponseEntity.ok(Result.deleted(employeeService.delete(id)));
    }

    @Override
    public ResponseEntity importFromExcelFile(MultipartFile file) {
        return mapToResponse(employeeService.importFromExcel(file, new EmployeeRequest()));
    }

    @Override
    public ResponseEntity<ByteArrayResource> downloadExcelFile() {
        return exportFileService.downloadFileFromServer(employeeService.exportAll());
    }

    private ResponseEntity<Result> mapToResponse(Object o) {
        return ResponseEntity.ok(Result.ok(o));
    }

    @PostMapping("/save/img/{id}")
    public ResponseEntity uploadAttachment(@PathVariable("id") Long id, @RequestParam("files") MultipartFile file) {
        return ResponseEntity.ok(employeeService.uploadImg(id, file));
    }

}
