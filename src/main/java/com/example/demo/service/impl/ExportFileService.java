package com.example.demo.service.impl;

import com.example.demo.exceptions.BadRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.shaded.apache.poi.util.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class, BadRequest.class})
public class ExportFileService {

    @SneakyThrows
    public ResponseEntity<ByteArrayResource> downloadFileFromServer(String fileId) {
        File file = new File(fileId);

        return file.exists() ?
                ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                        .body(new ByteArrayResource(IOUtils.toByteArray(new FileInputStream(file))))
                :
                null;

    }


    @SneakyThrows
    public ResponseEntity<ByteArrayResource> previewFileFromServer(String fileId) {
        File file = new File(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                .body(new ByteArrayResource(IOUtils.toByteArray(new FileInputStream(file))));
    }


}
