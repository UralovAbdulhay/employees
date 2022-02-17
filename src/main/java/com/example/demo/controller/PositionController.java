package com.example.demo.controller;

import com.example.demo.controller.endpoint.PositionEndpoint;
import com.example.demo.payload.Result;
import com.example.demo.payload.requests.PositionRequest;
import com.example.demo.service.impl.PositionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PositionController implements PositionEndpoint {

    private final PositionServiceImpl positionService;

    @Override
    public ResponseEntity<Result> saveOrUpdate(PositionRequest request) {
        System.out.println(request.toString());
        System.out.println(request.getId());
        return mapper(positionService.saveOrUpdate(request));
    }

    @Override
    public ResponseEntity<Result> get(Long id) {
        return mapper(positionService.findById(id));

    }

    @Override
    public ResponseEntity<Result> getAll(int page, int size) {
        return mapper(positionService.findAll(page, size));
    }

    @Override
    public ResponseEntity<Result> delete(Long id) {
        return ResponseEntity.ok(Result.deleted(positionService.delete(id)));
    }

    private ResponseEntity<Result> mapper(Object o) {
        return ResponseEntity.ok(Result.ok(o));
    }
}
