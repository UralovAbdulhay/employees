package com.example.demo.controller;


import com.example.demo.base.BaseURL;
import com.example.demo.file.fileInStorage.InStorageFileService;
import com.example.demo.payload.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 36000000)
public class InStorageFileController {


    private final InStorageFileService inStorageFileService;


    @PostMapping("/save")
    public ResponseEntity uploadFile(@RequestParam("files") MultipartFile file) {
        System.out.println(file);
        return ResponseEntity.ok(inStorageFileService.save(file, BaseURL.COMMON_FILE_PATH));
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity downloadFile(@PathVariable @NotBlank String fileId) {
        return inStorageFileService.downloadFile(fileId);
    }


    @GetMapping("/preview/{fileId}")
    public ResponseEntity previewFile(@PathVariable @NotBlank String fileId) {
        return inStorageFileService.previewFile(fileId);
    }


    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity delete(@PathVariable String hashId) throws MalformedURLException {
        return mapToResponse(inStorageFileService.delete(hashId));
    }

    private ResponseEntity<Result> mapToResponse(Object o) {
        return ResponseEntity.ok(Result.ok(o));
    }


}
