package com.example.demo.controller;

import com.example.demo.controller.endpoint.DepartmentEndpoint;
import com.example.demo.file.MyFileService;
import com.example.demo.payload.Result;
import com.example.demo.payload.requests.DepartmentRequest;
import com.example.demo.service.impl.DepartmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class DepartmentController implements DepartmentEndpoint {

    private final DepartmentServiceImpl departmentService;
    private final MyFileService myFileService;


    @Override
    public ResponseEntity<Result> save(DepartmentRequest request) {
        System.out.println(request);

        return mapToResponse(departmentService.save(request));
    }

    @Override
    public ResponseEntity<Result> update(DepartmentRequest request) {
        return mapToResponse(departmentService.update(request));
    }

    @Override
    public ResponseEntity<Result> get(@Min(1) @Valid @NotNull Long id) {
        return mapToResponse(departmentService.findById(id));

    }

    @Override
    public ResponseEntity<Result> getAll(int page, int size) {
        return mapToResponse(departmentService.findAll(page, size));
    }

    @Override
    public ResponseEntity<Result> delete(Long id) {
        return ResponseEntity.ok(Result.deleted(departmentService.delete(id)));
    }

    @Override
    public ResponseEntity uploadFile(MultipartFile file) {
        System.out.println(file.getContentType());
        return mapToResponse(departmentService.importFromExcel(file, new DepartmentRequest()));
    }

    @GetMapping("/export")
    public ResponseEntity downloadExcelFile() {
        return myFileService.downloadFileFromServer(departmentService.exportAll());
    }

    private ResponseEntity<Result> mapToResponse(Object o) {
        return ResponseEntity.ok(Result.ok(o));
    }
}
