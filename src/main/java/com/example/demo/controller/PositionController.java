package com.example.demo.controller;

import com.example.demo.controller.endpoint.PositionEndpoint;
import com.example.demo.payload.Result;
import com.example.demo.payload.requests.PositionRequest;
import com.example.demo.service.impl.PositionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PositionController implements PositionEndpoint {

    private final PositionServiceImpl positionService;

    @Override
    public ResponseEntity<Result> save(PositionRequest request) {
        return mapToResponse(positionService.save(request));
    }

    @Override
    public ResponseEntity<Result> update(PositionRequest request) {
        return mapToResponse(positionService.update(request));
    }

    @Override
    public ResponseEntity<Result> get(Long id) {
        return mapToResponse(positionService.findById(id));

    }

    @Override
    public ResponseEntity<Result> getAll(int page, int size) {
        return mapToResponse(positionService.findAll(page, size));
    }

    @Override
    public ResponseEntity<Result> delete(Long id) {
        return ResponseEntity.ok(Result.deleted(positionService.delete(id)));
    }

    @Override
    public ResponseEntity uploadFile(MultipartFile file) {
        System.out.println(file.getSize());
        System.out.println(file.getOriginalFilename());
        return mapToResponse(positionService.importFromExcel(file, new PositionRequest()));
    }

    private ResponseEntity<Result> mapToResponse(Object o) {
        return ResponseEntity.ok(Result.ok(o));
    }
}
