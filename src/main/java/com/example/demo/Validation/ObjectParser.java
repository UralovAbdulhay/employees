package com.example.demo.Validation;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ObjectParser {


    static Object copyFieldsIgnoreNulls(Object dest, Object src, boolean isIgnore) {


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

        return dest;

    }

}
