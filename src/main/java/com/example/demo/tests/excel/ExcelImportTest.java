package com.example.demo.tests.excel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gembox.spreadsheet.*;

import java.util.ArrayList;
import java.util.List;

public class ExcelImportTest {

    public static void main(String[] args) throws java.io.IOException {
        // If using Professional version, put your serial key below.
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");

        ExcelFile workbook = ExcelFile.load("Hello World.xlsx");


        List<Xodim> xodims = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        // Iterate through all worksheets in an Excel workbook.
        for (ExcelWorksheet worksheet : workbook.getWorksheets()) {
            sb.append('\n');
            sb.append(String.format("%1$s %2$s %1$s", "-----", worksheet.getName()));
            System.out.println(worksheet.getRows().size());

            // Iterate through all rows in an Excel worksheet.
            for (ExcelRow row : worksheet.getRows()) {
                sb.append('\n');

                // Iterate through all allocated cells in an Excel row.
                for (ExcelCell cell : row.getAllocatedCells()) {
                    if (cell.getValueType() != CellValueType.NULL)
                        sb.append(String.format("%1$s [%2$s]           ", cell.getValue(), cell.getValueType()));
                    else
                        sb.append("                        ");
                }
            }
        }

        ObjectMapper mapper = new ObjectMapper();

        System.out.println(sb.toString());
    }


}
