package com.example.demo.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.base.BaseURL.*;

public interface BaseEndpoint<T> {

    @PostMapping(SAVE_OR_UPDATE)
    ResponseEntity<?> saveOrUpdate(@RequestBody T request);

    @GetMapping(GET + "/{id}")
    ResponseEntity<?> get(@PathVariable(name = "id") Long id);


    @GetMapping(GET_ALL)
    ResponseEntity<?> getAll(@RequestParam int page, @RequestParam int size);

    @DeleteMapping(DELETE + "/{id}")
    ResponseEntity<?> delete(@PathVariable(name = "id") Long id);


}
