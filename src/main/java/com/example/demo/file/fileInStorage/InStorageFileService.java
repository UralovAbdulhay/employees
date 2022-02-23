package com.example.demo.file.fileInStorage;


import com.example.demo.entity.other.ExportedFile;
import com.example.demo.exceptions.BadRequest;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.payload.Result;
import com.example.demo.service.impl.ExportedFileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class, BadRequest.class})
public class InStorageFileService {

    private final InStorageFileRepository inStorageFileRepository;
    private final ExportedFileService exportedFileService;


    public InStorageFile save(MultipartFile multipartFile, String category) {
        try {
            if (multipartFile.getSize() == 0) {
                throw BadRequest.get("MultipartFile ish empty!");
            }

            InStorageFile inStorageFile =
                    new InStorageFile(
                            UUID.randomUUID().toString(),
                            multipartFile.getOriginalFilename(),
                            multipartFile.getContentType(),
                            getExtension(Objects.requireNonNull(multipartFile.getOriginalFilename())),
                            multipartFile.getSize(),
                            multipartFile.getBytes()
                    );

            String filePath = String.format("files/%s/%s/%s",
                    category,
                    DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDateTime.now()),
                    inStorageFile.getExtension()
            );

            String fileName = String.format("/%s.%s",
                    inStorageFile.getHashId(),
                    inStorageFile.getExtension()
            );

            File file = new File(filePath, "/" + fileName);

            try {
                file.getParentFile().mkdirs();
                // copy bytes into new file or saving into storage
                Files.copy(multipartFile.getInputStream(), Path.of(file.getPath()));

                ExportedFile exportedFile = new ExportedFile();
                exportedFile.setFilePath(file.getPath());
                exportedFileService.save(exportedFile);

                System.out.println(file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            inStorageFile.setUploadPath(file.exists() ? filePath : null);

            // save MyFile into base
            inStorageFileRepository.save(inStorageFile);

            return inStorageFile;
        } catch (IOException e) {
            e.printStackTrace();
            throw BadRequest.get(e.getMessage());
        }
    }


//    public Result saveAll(List<MultipartFile> multipartFiles) {
//        try {
//            if (multipartFiles.stream().anyMatch(e -> e.getSize() == 0)) {
//                throw BadRequest.get("MultipartFile ish empty!");
//            }
//            List<InStorageFile> inStorageFiles = null;
//            inStorageFiles = multipartFiles.stream().map(e -> {
//                InStorageFile inStorageFile = new InStorageFile(
//                        UUID.randomUUID().toString(),
//                        e.getOriginalFilename(),
//                        e.getContentType(),
//                        e.getSize(),
//                        getExtension(e.getName())
//                );
//
//                try {
//                    inStorageFile.setData(e.getBytes());
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                    inStorageFile = null;
//                }
//                return inStorageFile;
//            })
//                    .collect(Collectors.toList());
//            System.out.println("\n****************************************\n");
//            System.out.println("myFile from request = \n" + inStorageFiles);
//            System.out.println("\n****************************************\n");
//
//            // save MyFile into base
//            inStorageFileRepository.saveAll(inStorageFiles);
//            return new Result("File successfully saved!", true, inStorageFiles);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw BadRequest.get(e.getMessage());
//        }
//    }


    @SneakyThrows
    public ResponseEntity<?> downloadFile(String fileId) {
        InStorageFile inStorageFile = findByHashId(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(inStorageFile.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + inStorageFile.getName() + "\"")
                .body(
                        inStorageFile.getUploadPath() != null
                                ?
                                new FileUrlResource(String.format("%s/%s.%s", inStorageFile.getUploadPath(), inStorageFile.getHashId(), inStorageFile.getExtension()))
                                :
                                new ByteArrayResource(inStorageFile.getData())
                );
    }


    @SneakyThrows
    public ResponseEntity<?> previewFile(String fileId) {
        InStorageFile inStorageFile = findByHashId(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(inStorageFile.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + inStorageFile.getName() + "\"")
                .body(
                        inStorageFile.getUploadPath() != null
                                ?
                                new FileUrlResource(String.format("%s/%s.%s", inStorageFile.getUploadPath(), inStorageFile.getHashId(), inStorageFile.getExtension()))
                                :
                                new ByteArrayResource(inStorageFile.getData())
                );
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public InStorageFile findByHashId(String hashId) {
        return inStorageFileRepository.findByHashId(hashId).orElseThrow(() -> new ResourceNotFound("MyFile", "hashId", hashId));
    }

    public List<InStorageFile> findAllByHashId(List<String> hashId) {
        return inStorageFileRepository.findByHashIdIn(hashId);
    }

    public Result delete(String hashId) {
        InStorageFile file = findByHashId(hashId);

        if (file.getUploadPath() != null) {
            File file1 = new File(getFilePath(file));
            file1.delete();
        }
        inStorageFileRepository.delete(file);
        return Result.deleted(!inStorageFileRepository.existsByHashId(hashId));
    }


    private String getFilePath(InStorageFile inStorageFile) {
        return String.format("%s/%s.%s", inStorageFile.getUploadPath(), inStorageFile.getHashId(), inStorageFile.getExtension());
    }

    @SneakyThrows
    public File getFileIO(InStorageFile inStorageFile) {

        File file = new File(getFilePath(inStorageFile));
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            Files.copy(new ByteArrayResource(inStorageFile.getData()).getInputStream(), Path.of(file.getPath()));
        }
        return file;
    }

    public File getFileIO(String hasId) {
        return getFileIO(findByHashId(hasId));
    }


}
