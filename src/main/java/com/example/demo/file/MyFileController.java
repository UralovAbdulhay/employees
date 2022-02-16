package com.example.demo.file;



import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/admin/file")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 36000000)
public class MyFileController {


    private final MyFileService myFileService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save-all")
    public ResponseEntity uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) {
        System.out.println(files);
        return ResponseEntity.ok(myFileService.saveAll(files));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity uploadFile(@RequestParam("files") MultipartFile file) {
        System.out.println(file);
        return ResponseEntity.ok(myFileService.save(file));
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable @NotBlank String fileId) {
        return myFileService.downloadFile(fileId);
    }


    @GetMapping("/preview/{fileId}")
    public ResponseEntity<ByteArrayResource> previewFile(@PathVariable @NotBlank String fileId) {
//        System.out.println(fileId);
        return myFileService.previewFile(fileId);
    }


}
