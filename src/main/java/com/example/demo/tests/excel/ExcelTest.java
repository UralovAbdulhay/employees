package com.example.demo.tests.excel;

import com.gembox.spreadsheet.ExcelFile;
import com.gembox.spreadsheet.ExcelWorksheet;
import com.gembox.spreadsheet.SpreadsheetInfo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ExcelTest {


    public static void main(String[] args) throws java.io.IOException {
        // If using Professional version, put your serial key below.
//        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
//
//        ExcelFile workbook = new ExcelFile();
//        ExcelWorksheet worksheet = workbook.addWorksheet("Hello World");
//
//        worksheet.getCell(0, 0).setValue("English:");
//        worksheet.getCell(0, 1).setValue("Hello");
//
//        worksheet.getCell(1, 0).setValue("Russian:");
//        // Using UNICODE string.
//        worksheet.getCell(1, 1).setValue(new String(new char[]{'\u0417', '\u0434', '\u0440', '\u0430', '\u0432', '\u0441', '\u0442', '\u0432', '\u0443', '\u0439', '\u0442', '\u0435'}));
//
//        worksheet.getCell(2, 0).setValue("Chinese:");
//        // Using UNICODE string.
//        worksheet.getCell(2, 1).setValue(new String(new char[]{'\u4f60', '\u597d'}));
//
//        worksheet.getCell(4, 0).setValue("In order to see Russian and Chinese characters you need to have appropriate fonts on your PC.");
//        worksheet.getCells().getSubrangeAbsolute(4, 0, 4, 7).setMerged(true);
//
//        workbook.save("Hello World.xlsx");


        List<Object> departments = List.of(
                new Xodim("Abdulhay", "Uralov", 23, "Sergili"),
                new Xodim("Muhriddin", "O'ralov", 21, "Xorazm"),
                new Xodim("Doni", "Muqsinov", 45, "toshkent"),
                new Xodim("Ali", "Aliyev", 5, "Buxora"),
                new Xodim("Olim", "qoraboyev", 354, "Chilonzor")
        );
        System.out.println(export(departments));

    }


    static String export(List<Object> objects) {


//        if (!objects.isEmpty()) {

        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");

        ExcelFile workbook = new ExcelFile();
        ExcelWorksheet worksheet = workbook.addWorksheet("Hello World");

        Object header = objects.get(0);
        Field[] headerFields = header.getClass().getDeclaredFields();
        int columnSize = headerFields.length;
        AtomicInteger rowIndex = new AtomicInteger();

        for (int i = 0; i < columnSize; i++) {
            headerFields[i].setAccessible(true);
            worksheet.getCell(rowIndex.get(), i).setValue(headerFields[i].getName());
        }
        rowIndex.incrementAndGet();
        objects.forEach(
                e -> {

                    Field[] field = e.getClass().getDeclaredFields();
                    for (int i = 0; i < field.length; i++) {
                        field[i].setAccessible(true);
                        try {
                            worksheet.getCell(rowIndex.get(), i)
                                    .setValue(field[i].get(e));

                        } catch (IllegalAccessException ex) {
                            ex.printStackTrace();
                        }
                    }

                    rowIndex.incrementAndGet();

                });


        try {


            String fileName = String.format("files/export/%s/export-%s.xlsx",
                    DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDateTime.now()),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss SSS").format(LocalDateTime.now()));
            new File(fileName).getParentFile().mkdirs();
            workbook.save(fileName);

            return fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }


//        }


    }

//    public static void main(String[] args) {
//
//        System.out.println(
//                String.format("%2$s %1$s", "salom", "dunyo" )
//        );
//
//    }


}






