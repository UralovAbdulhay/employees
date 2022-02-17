package com.example.demo.tests.reflection;

import com.example.demo.tests.others.Student1;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Reflection {

    public static void main(String[] args) {

        long l = System.currentTimeMillis();

        RefType_1 type1 = new RefType_1(null, "UraLOV", 54, "Sergili", 21.2, LocalDateTime.now());

        RefType_2 type2 = new RefType_2("nODIR", null, 32);

        System.out.println("main");
        System.out.println(type1);
        System.out.println(type2);
        System.out.println("main");


//        List<Field> fields_1 = List.of(type1.getClass().getDeclaredFields());
//
//        List<Field> fields_2 = List.of(type2.getClass().getDeclaredFields());
//
//
//        System.out.println(fields_1.stream().map(Field::getName).collect(Collectors.toList()));
//        System.out.println(type1);
//        System.out.println();
//        System.out.println(type2);
//        System.out.println(fields_2.stream().map(Field::getName).collect(Collectors.toList()));
//

        Student1 student1 = new Student1();
        student1.setName("Muhriddin");

        copyFieldsIgnoreNulls(student1, type1, false);

        System.out.println();
        System.out.println("main");
        System.out.println(type1);
        System.out.println(type2);
        System.out.println(student1);
        System.out.println("main");

        System.out.println(System.currentTimeMillis() - l);
    }


    static void copyFields(Object dest, Object src) {


        Map<String, Field> destFieldMap = List.of(dest.getClass().getDeclaredFields())
                .stream()
                .collect(Collectors.toMap(Field::getName, e -> e));


        Map<String, Field> srcFieldMap = List.of(src.getClass().getDeclaredFields())
                .stream()
                .collect(Collectors.toMap(Field::getName, e -> e));

        System.out.println("***********************");
        System.out.println(destFieldMap.keySet());
        System.out.println(srcFieldMap.keySet());
        System.out.println("***********************");


        final Set<String> destFields = destFieldMap.keySet();
        srcFieldMap.keySet().forEach(
                e -> {

                    if (destFields.contains(e)) {
                        Field field = destFieldMap.get(e);
                        field.setAccessible(true);
                        srcFieldMap.get(e).setAccessible(true);
                        try {
                            field.set(
                                    dest,
                                    srcFieldMap.get(e).get(src)
                            );
                        } catch (IllegalAccessException ex) {
                            try {
                                field.set(
                                        dest,
                                        null
                                );
                            } catch (IllegalAccessException exc) {
                                exc.printStackTrace();
                            }

                            ex.printStackTrace();
                        }

                        destFieldMap.put(e, field);
                    }
                }
        );


    }


    static void copyFieldsIgnoreNulls(Object dest, Object src, boolean isIgnore) {


        Map<String, Field> destFieldMap = List.of(dest.getClass().getDeclaredFields())
                .stream()
                .collect(Collectors.toMap(Field::getName, e -> e));


        Map<String, Field> srcFieldMap = List.of(src.getClass().getDeclaredFields())
                .stream()
                .collect(Collectors.toMap(Field::getName, e -> e));


        System.out.println("***********************");
        System.out.println(destFieldMap.keySet());
        System.out.println(srcFieldMap.keySet());
        System.out.println("***********************");


        final Set<String> destFields = destFieldMap.keySet();
        srcFieldMap.keySet().forEach(
                e -> {

                    try {
                        srcFieldMap.get(e).setAccessible(true);
                        boolean b = !isIgnore || srcFieldMap.get(e).get(src) != null;

                        if (b && destFields.contains(e)) {
                            Field field = destFieldMap.get(e);
                            field.setAccessible(true);
                            try {
                                field.set(
                                        dest,
                                        srcFieldMap.get(e).get(src)
                                );
                            } catch (IllegalAccessException ex) {
                                try {
                                    field.set(
                                            dest,
                                            null
                                    );
                                } catch (IllegalAccessException exc) {
                                    exc.printStackTrace();
                                }

                                ex.printStackTrace();
                            }

                            destFieldMap.put(e, field);
                        }

                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
        );


    }


}
