package com.example.demo.tests.excel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gembox.spreadsheet.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExcelImportTest {

    public static void main(String[] args) throws java.io.IOException {
        // If using Professional version, put your serial key below.
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");

//        ExcelFile workbook = ExcelFile.load();
        ExcelFile workbook = ExcelFile.load("Hello World.xlsx");


        int rowCount = workbook.getWorksheet(0).getRows().size();

        ExcelRowCollection rows = workbook.getWorksheet(0).getRows();

        int headerRowIndex = -1;

        List<HashMap<String, Object>> hashMaps = new ArrayList<>();

        for (int i = 0; i < rows.size(); i++) {
            ExcelCellCollection cells = rows.get(i).getAllocatedCells();
            HashMap<String, Object> map = new HashMap<>();


            for (int j = 0; j < cells.size(); j++) {
                ExcelCell cell = cells.get(j);
                if (cell.getValueType() != CellValueType.NULL) {
                    if (headerRowIndex == -1) {
                        headerRowIndex = i;
                    } else {
                        map.put(String.valueOf(rows.get(headerRowIndex).getCell(j).getValue()), cell.getValue());

                        System.out.print(cell.getValue() + "\t\t");
                    }
                }
            }

            if (!map.isEmpty()) {
                hashMaps.add(map);
            }
            System.out.println();


        }


        System.out.println();
        System.out.println(hashMaps);

        System.out.println(headerRowIndex);

        ObjectMapper mapper = new ObjectMapper();

        List<Xodim> xodimList = hashMaps
                .stream()
                .map(e -> {

                    try {
                        return mapper.convertValue(e, Xodim.class);
                    } catch (IllegalArgumentException ex) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());


        System.out.println();
        System.out.println(xodimList);
        System.out.println(xodimList.size());
        System.out.println(xodimList.get(0));
        System.out.println(xodimList.get(0).getAddress());


    }


}
