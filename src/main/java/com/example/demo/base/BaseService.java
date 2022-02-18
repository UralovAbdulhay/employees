package com.example.demo.base;

import com.example.demo.exceptions.BadRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gembox.spreadsheet.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
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


}
