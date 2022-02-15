package com.example.demo.tests;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MapTest {

    public static void main(String[] args) {

        Object abdulhay = new Student1("Abdulhay", 12, true);
        Object  muqadas = new Student2("Muqadas", 10, false, "null");

        test(muqadas, abdulhay);
    }

    static void test(Object o1, Object o2) {

        ObjectMapper mapper = new ObjectMapper();


        System.out.println(mapper.convertValue(o1, o2.getClass()));

    }

}
