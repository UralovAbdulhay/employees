package com.example.demo.Validation;

import com.example.demo.base.BaseEntity;
import com.example.demo.base.BaseRequest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ObjectParser {


    public static <D extends BaseEntity, S extends BaseRequest> D copyFieldsIgnoreNulls(D dest, S src, boolean isNullIgnore) {


        List<Field> destFieldList = new ArrayList<>(List.of(dest.getClass().getDeclaredFields()));
        destFieldList.addAll(List.of(dest.getClass().getSuperclass().getDeclaredFields()));
        Map<String, Field> destFieldMap = destFieldList
                .stream()
                .collect(Collectors.toMap(Field::getName, e -> e));


        List<Field> srcFieldList = new ArrayList<>(List.of(src.getClass().getDeclaredFields()));
        srcFieldList.addAll(List.of(src.getClass().getSuperclass().getDeclaredFields()));
        Map<String, Field> srcFieldMap = srcFieldList
                .stream()
                .collect(Collectors.toMap(Field::getName, e -> e));


        System.out.println("***********************");
        System.out.println(destFieldMap);
        System.out.println(srcFieldMap);
        System.out.println("***********************");

        System.out.printf("%s,\t%s,\t%s,\t%s,\t%s",
                "getName",
                "isSynthetic",
                "getType",
                "getType",
                "isEnumConstant"
        );
        System.out.println();

        destFieldMap.keySet().forEach(
                e -> {

                    System.out.printf("%s,\t\t%s",
                            destFieldMap.get(e).getName(),
                            destFieldMap.get(e).getType()
                    );

                    System.out.println();
                }
        );


        final Set<String> destFields = destFieldMap.keySet();
        srcFieldMap.keySet().forEach(
                e -> {

                    try {

                        srcFieldMap.get(e).setAccessible(true);
                        boolean b = !isNullIgnore || srcFieldMap.get(e).get(src) != null;

                        if (b && destFields.contains(e)) {
                            Field field = destFieldMap.get(e);
                            field.setAccessible(true);
                            try {
                                field.set(
                                        dest,
                                        srcFieldMap.get(e).get(src)
                                );
                            } catch (IllegalAccessException ex) {
//                                try {
//                                    field.set(
//                                            dest,
//                                            null
//                                    );
//                                } catch (IllegalAccessException exc) {
//                                    exc.printStackTrace();
//                                }

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
