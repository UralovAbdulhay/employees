package com.example.demo.tests.others;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MapTest {

    public static void main(String[] args) {

        Object student1 = new Student1("Student1", 12, true);
        Object student2 = new Student2("Student2", 10, false, "null");

        test(student2, student1);
    }

    static void test(Object o1, Object o2) {

        ObjectMapper mapper = new ObjectMapper();


        System.out.println(mapper.convertValue(o1, o2.getClass()));


    }



}
