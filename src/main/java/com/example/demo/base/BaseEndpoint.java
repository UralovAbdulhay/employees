package com.example.demo.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import  static com.example.demo.base.BaseURL.*;

public interface BaseEndpoint<T> {

    @PostMapping(SAVE_OR_UPDATE)
    ResponseEntity<?> saveOrUpdate(@RequestBody T request);

    @GetMapping(GET)
    //PathVaribleni yozish kere
    ResponseEntity<?> get(@RequestBody T request);



}
