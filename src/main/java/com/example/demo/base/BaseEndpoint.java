package com.example.demo.base;

import com.example.demo.payload.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.base.BaseURL.*;

public interface BaseEndpoint<T> {

    @PostMapping(SAVE_OR_UPDATE)
    ResponseEntity<Result> saveOrUpdate(@RequestBody T request);

    @GetMapping(GET + "/{id}")
    ResponseEntity<Result> get(@PathVariable(name = "id") Long id);


    @GetMapping(GET_ALL)
    ResponseEntity<Result> getAll(@RequestParam int page, @RequestParam int size);

    @DeleteMapping(DELETE + "/{id}")
    ResponseEntity<Result> delete(@PathVariable(name = "id") Long id);


}
