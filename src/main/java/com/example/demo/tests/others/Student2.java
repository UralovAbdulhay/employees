package com.example.demo.tests.others;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Student2 {

    String name;
    int age;
    boolean gender;
    String address;

}
