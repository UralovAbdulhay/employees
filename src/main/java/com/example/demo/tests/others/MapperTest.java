package com.example.demo.tests.others;

import com.example.demo.tests.excel.Xodim;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class MapperTest {


    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("name", "654");
        map.put("surname", "Uralov");
//        map.put("age", "23");
        map.put("address", "Sergili");
        map.put("department", "Sergili");


        Xodim xodim = mapper.convertValue(map, Xodim.class);

        System.out.println(xodim);


    }

}
