package com.example.demo.base;

import com.example.demo.exceptions.BadRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gembox.spreadsheet.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service

public interface BaseService<E, R> {


    E save(R request);

    E update(R request);

    default List<E> saveAll(Collection<R> requests) {
        return requests.stream().map(this::save).collect(Collectors.toList());
    }

    default List<E> updateAll(Collection<R> requests) {
        return requests.stream().map(this::update).collect(Collectors.toList());
    }

    E findById(Long id);

    List<E> findAll(int page, int size);

    boolean delete(Long id);

    boolean isDeleted(Long id);

    boolean existById(Long id);

    boolean isValidForUpdate(R request);

    boolean isValidForSave(R request);

    R convertToPayload(E entity);

    default List<R> convertToPayload(List<E> entities) {
        return entities
                .stream()
                .map(this::convertToPayload)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    default List<R> importFromExcel(MultipartFile multipartFile, R entity) {

        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");

        if (multipartFile.getSize() == 0) {
            throw BadRequest.get("MultipartFile ish empty!");
        }

        ExcelFile workbook = ExcelFile.load(multipartFile.getInputStream());

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
                    }
                }
            }

            if (!map.isEmpty()) {
                hashMaps.add(map);
            }

        }

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(hashMaps);
        List<R> resultList = hashMaps
                .stream()
                .map(e -> {

                    try {
                        return (R) mapper.convertValue(e, entity.getClass());
                    } catch (IllegalArgumentException ex) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        System.out.println(resultList);

        return resultList;
    }


    default String exportToExcel(List<R> objects) {


//        if (!objects.isEmpty()) {

        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");

        ExcelFile workbook = new ExcelFile();
        ExcelWorksheet worksheet = workbook.addWorksheet("Export");

        Object header = objects.get(0);
        Field[] headerFields = header.getClass().getDeclaredFields();
        int columnSize = headerFields.length;
        AtomicInteger rowIndex = new AtomicInteger();

        int firstRow = 0, firstCol = 0, lastRow = headerFields.length, lastCol = objects.size();

        for (int i = 0; i < columnSize; i++) {
            headerFields[i].setAccessible(true);
            worksheet.getCell(rowIndex.get(), i).setValue(headerFields[i].getName());
            worksheet.getCell(rowIndex.get(), i).getStyle().getFont().setWeight(ExcelFont.BOLD_WEIGHT);
//            worksheet.getCell(rowIndex.get(), i).getStyle().getBorders().setBorders(MultipleBorders.all(), SpreadsheetColor.fromName(ColorName.BLACK), LineStyle.THIN);
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

        CellRange cells = worksheet.getUsedCellRange(true);
        cells.getStyle().getBorders().setBorders(MultipleBorders.all(), SpreadsheetColor.fromName(ColorName.BLACK), LineStyle.THIN);


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

    }

    String exportAll();

}
