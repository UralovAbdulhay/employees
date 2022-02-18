package com.example.demo.base;

import com.example.demo.payload.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.demo.base.BaseURL.*;

public interface BaseEndpoint<T> {

    @PostMapping(SAVE)
    ResponseEntity<Result> save(@RequestBody T request);

    @PostMapping(UPDATE)
    ResponseEntity<Result> update(@RequestBody T request);

    @GetMapping(GET + "/{id}")
    ResponseEntity<Result> get(@PathVariable(name = "id") Long id);


    @GetMapping(GET_ALL)
    ResponseEntity<Result> getAll(@RequestParam int page, @RequestParam int size);

    @DeleteMapping(DELETE + "/{id}")
    ResponseEntity<Result> delete(@PathVariable(name = "id") Long id);

    @PostMapping("/import")
    ResponseEntity uploadFile(@RequestParam("file") MultipartFile file);


}
