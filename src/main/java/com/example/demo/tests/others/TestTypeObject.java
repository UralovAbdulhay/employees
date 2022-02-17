package com.example.demo.tests.others;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestTypeObject {

    public static void main(String[] args) {

        List o = new ArrayList<String>();
        Object o2 = new LinkedList<Integer>();

        System.out.println(o.getClass());
        System.out.println(o2.getClass());


    }

}
