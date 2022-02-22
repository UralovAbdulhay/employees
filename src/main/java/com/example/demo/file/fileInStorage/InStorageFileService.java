//package com.example.demo.file.fileInStorage;
//
//
//import com.example.demo.exceptions.BadRequest;
//import com.example.demo.exceptions.ResourceNotFound;
//import com.example.demo.payload.Result;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import org.shaded.apache.poi.util.IOUtils;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.data.domain.Example;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.List;
//import java.util.Objects;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class, BadRequest.class})
//public class InStorageFileService {
//
//    private final InStorageFileRepository inStorageFileRepository;
//
//
//    public Result save(MultipartFile multipartFile) {
//        try {
//            if (multipartFile.getSize() == 0) {
//                throw BadRequest.get("MultipartFile ish empty!");
//            }
//
//            InStorageFile inStorageFile =
//                    new InStorageFile(
//                    UUID.randomUUID().toString(),
//                    multipartFile.getOriginalFilename(),
//                    multipartFile.getContentType(),
//                    getExtension(Objects.requireNonNull(multipartFile.getOriginalFilename())),
//                    multipartFile.getSize(),
//                    multipartFile.getBytes()
//            );
//
//
//
//            // save MyFile into base
//            inStorageFileRepository.save(inStorageFile);
//            System.out.println("\n save myFile %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
//            System.out.println(inStorageFile);
//            System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
//
//            return new Result("File successfully saved!", true, inStorageFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw BadRequest.get(e.getMessage());
//        }
//    }
//
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
//
//
//    public ResponseEntity<ByteArrayResource> downloadFile(String fileId) {
//        InStorageFile inStorageFile = findByHashId(fileId);
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(inStorageFile.getContentType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + inStorageFile.getName() + "\"")
//                .body(new ByteArrayResource(inStorageFile.getData()));
//    }
//
//
//    public ResponseEntity<ByteArrayResource> previewFile(String fileId) {
//        InStorageFile inStorageFile = findByHashId(fileId);
////        System.out.println("previewFile = " + myFile);
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(inStorageFile.getContentType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + inStorageFile.getName() + "\"")
//                .body(new ByteArrayResource(inStorageFile.getData()));
//    }
//
//    @SneakyThrows
//    public ResponseEntity<ByteArrayResource> downloadFileFromServer(String fileId) {
//        File file = new File(fileId);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
//                .body(new ByteArrayResource(IOUtils.toByteArray(new FileInputStream(file))));
//
//    }
//
//
//    @SneakyThrows
//    public ResponseEntity<ByteArrayResource> previewFileFromServer(String fileId) {
//        File file = new File(fileId);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
//                .body(new ByteArrayResource(IOUtils.toByteArray(new FileInputStream(file))));
//    }
//
//
//    private String getExtension(String fileName) {
//        return fileName.substring(fileName.lastIndexOf(".") + 1);
//    }
//
//    public InStorageFile findByHashId(String hashId) {
//        return inStorageFileRepository.findByHashId(hashId).orElseThrow(() -> new ResourceNotFound("MyFile", "hashId", hashId));
//    }
//
//
//    public List<InStorageFile> findAllByHashId(List<String> hashId) {
//        return inStorageFileRepository.findByHashIdIn(hashId);
//    }
//
//    public Result delete(String hashId) {
//        inStorageFileRepository.delete(findByHashId(hashId));
//        return Result.deleted(!inStorageFileRepository.existsByHashId(hashId));
//    }
//
//    public Result deleteAllByHashId(List<String> hashId) {
//        inStorageFileRepository.deleteAll(findAllByHashId(hashId));
//        return Result.deleted(!inStorageFileRepository.existsByHashId(hashId.get(0)));
//    }
//
//    public Result deleteAll(List<InStorageFile> hashId) {
//        hashId.get(0);
//        inStorageFileRepository.deleteAll(hashId);
//        return Result.deleted(!inStorageFileRepository.exists(Example.of(hashId.get(0))));
//    }
//
//
//}
