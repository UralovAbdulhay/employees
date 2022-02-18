package com.example.demo.Validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObjectParser {


    public <D, S> D copyFieldsIgnoreNulls(D dest, S src, boolean isNullIgnore) {

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


//    public D copyFieldsIgnoreNulls(D dest, S src, boolean isNullIgnore) {
//
//
//        List<Field> destFieldList = new ArrayList<>(List.of(dest.getClass().getDeclaredFields()));
//        destFieldList.addAll(List.of(dest.getClass().getSuperclass().getDeclaredFields()));
//        Map<String, Field> destFieldMap = destFieldList
//                .stream()
//                .collect(Collectors.toMap(Field::getName, e -> e));
//
//
//        List<Field> srcFieldList = new ArrayList<>(List.of(src.getClass().getDeclaredFields()));
//        srcFieldList.addAll(List.of(src.getClass().getSuperclass().getDeclaredFields()));
//        Map<String, Field> srcFieldMap = srcFieldList
//                .stream()
//                .collect(Collectors.toMap(Field::getName, e -> e));
//
//
//        System.out.println("***********************");
//        System.out.println(destFieldMap);
//        System.out.println(srcFieldMap);
//        System.out.println("***********************");
//
//        System.out.println("####################");
//
//        System.out.printf("%s,\t\t\t%s,",
//                "getName",
//                "getType"
//
//        );
//        System.out.println();
////
////        destFieldMap.keySet().forEach(
////                e -> {
////
////                    System.out.printf("%s,\t\t%s",
////                            destFieldMap.get(e).getName(),
////                            destFieldMap.get(e).getType().getName()
////                    );
////
////                    if (!destFieldMap.get(e).getType().isPrimitive())
////                        try {
////
////                            Class<?> aClass = Class.forName(destFieldMap.get(e).getType().getName());
////
////                            if (aClass.getSuperclass() != null && aClass.getSuperclass().equals(BaseEntity.class)) {
////
////                                System.out.println("\naClass.getSuperclass = " + aClass.getSuperclass());
////                                System.out.println("\naClass.getSuperclass = " + aClass.getName());
////                            }
////
////                        } catch (ClassNotFoundException ex) {
////                            ex.printStackTrace();
////                        }
////
////
////                    System.out.println();
////                }
////        );
////
////        System.out.println("####################");
//
//
//        final Set<String> destFields = destFieldMap.keySet();
//        srcFieldMap.keySet().forEach(
//                e -> {
//
//                    try {
//
//                        srcFieldMap.get(e).setAccessible(true);
//                        boolean b = !isNullIgnore || srcFieldMap.get(e).get(src) != null;
//
//                        if (e.contains("Id")) {
//
//                            Field refField = destFieldMap.get(e.substring(0, e.indexOf("Id")));
//                            if (refField != null) {
//                                refField.setAccessible(true);
//
//                                try {
//
//                                    System.out.println(baseRepository.getClass().getName());
//                                    System.out.println(srcFieldMap.get(e).get(src));
////                                    BaseEntity baseEntity = baseRepository.findById((Long) srcFieldMap.get(e).get(src)).orElseThrow();
//                                    BaseEntity baseEntity = findById((Long) srcFieldMap.get(e).get(src), e.substring(0, e.indexOf("Id")));
//                                    System.out.println("baseEntity = " + baseEntity);
//                                    refField.set(
//                                            dest,
//                                            baseEntity
//                                    );
//                                } catch (IllegalAccessException ex) {
//                                    ex.printStackTrace();
//                                }
//
//                            }
//
//                        } else if (b && destFields.contains(e)) {
//                            Field field = destFieldMap.get(e);
//                            field.setAccessible(true);
//                            try {
//                                field.set(
//                                        dest,
//                                        srcFieldMap.get(e).get(src)
//                                );
//                            } catch (IllegalAccessException ex) {
////                                try {
////                                    field.set(
////                                            dest,
////                                            null
////                                    );
////                                } catch (IllegalAccessException exc) {
////                                    exc.printStackTrace();
////                                }
//                                ex.printStackTrace();
//                            }
//
//                            destFieldMap.put(e, field);
//                        }
//
//                    } catch (IllegalAccessException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//        );
//
//        return dest;
//
//    }


}
