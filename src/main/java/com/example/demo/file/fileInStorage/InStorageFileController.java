package com.example.demo.file.fileInStorage;



import com.example.demo.file.fileInDb.MyFileService;
import com.example.demo.payload.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 36000000)
public class InStorageFileController {


    private final InStorageFileService inStorageFileService;


    @PostMapping("/save-all")
    public ResponseEntity uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) {
        System.out.println(files);
        return ResponseEntity.ok(inStorageFileService.saveAll(files));
    }

      @PostMapping("/save")
    public ResponseEntity uploadFile(@RequestParam("files") MultipartFile file) {
        System.out.println(file);
        return ResponseEntity.ok(inStorageFileService.save(file));
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable @NotBlank String fileId) {
        return inStorageFileService.downloadFile(fileId);
    }


    @GetMapping("/preview/{fileId}")
    public ResponseEntity<ByteArrayResource> previewFile(@PathVariable @NotBlank String fileId) {
//        System.out.println(fileId);
        return inStorageFileService.previewFile(fileId);
    }


        @PostMapping("/save")
        public ResponseEntity saveFile(@RequestParam(name = "file") MultipartFile multipartFile) {
            return mapinStorageFileService.save(multipartFile);
        }


        @GetMapping("/preview/{hashId}")
        public ResponseEntity preview(@PathVariable String hashId) throws MalformedURLException {
            MyFile myFile = inStorageFileService
                    .findByHashId(hashId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.EXPIRES, "inline; fileName=" + URLEncoder.encode(myFile.getName()))
                    .contentType(MediaType.parseMediaType(myFile.getContentType()))
                    .body(new FileUrlResource(String.format("%s/%s.%s", myFile.getUploadPath(), myFile.getHashId(), myFile.getExtension())));
        }

        @GetMapping("/download/{hashId}")
        public ResponseEntity download(@PathVariable String hashId) throws MalformedURLException {
            MyFile myFile = inStorageFileService
                    .findByHashId(hashId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + URLEncoder.encode(myFile.getName()))
                    .contentType(MediaType.parseMediaType(myFile.getContentType()))
                    .body(new FileUrlResource(String.format("%s/%s.%s", myFile.getUploadPath(), myFile.getHashId(), myFile.getExtension())));
        }

        @DeleteMapping("/delete/{hashId}")
        public ResponseEntity delete(@PathVariable String hashId) throws MalformedURLException {
            return inStorageFileService
                    .delete(hashId);
        }

    private ResponseEntity<Result> mapToResponse(Object o) {
        return ResponseEntity.ok(Result.ok(o));
    }


}
