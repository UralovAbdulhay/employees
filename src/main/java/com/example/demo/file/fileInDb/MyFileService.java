//package com.example.demo.file.fileInDb;
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
//public class MyFileService {
//
//    private final MyFileRepository myFileRepository;
//
//
//    public Result save(MultipartFile multipartFile) {
//        try {
//            if (multipartFile.getSize() == 0) {
//                throw BadRequest.get("MultipartFile ish empty!");
//            }
//
//            MyFile myFile = null;
//            myFile = new MyFile(
//                    UUID.randomUUID().toString(),
//                    multipartFile.getOriginalFilename(),
//                    multipartFile.getContentType(),
//                    multipartFile.getSize(),
//                    multipartFile.getBytes(),
//                    getExtension(Objects.requireNonNull(multipartFile.getOriginalFilename()))
//            );
//
//
//            // save MyFile into base
//            myFileRepository.save(myFile);
//            System.out.println("\n save myFile %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
//            System.out.println(myFile);
//            System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
//
//            return new Result("File successfully saved!", true, myFile);
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
//            List<MyFile> myFiles = null;
//            myFiles = multipartFiles.stream().map(e -> {
//                MyFile myFile = new MyFile(
//                        UUID.randomUUID().toString(),
//                        e.getOriginalFilename(),
//                        e.getContentType(),
//                        e.getSize(),
//                        getExtension(e.getName())
//                );
//
//                try {
//                    myFile.setData(e.getBytes());
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                    myFile = null;
//                }
//                return myFile;
//            })
//                    .collect(Collectors.toList());
//            System.out.println("\n****************************************\n");
//            System.out.println("myFile from request = \n" + myFiles);
//            System.out.println("\n****************************************\n");
//
//            // save MyFile into base
//            myFileRepository.saveAll(myFiles);
//            return new Result("File successfully saved!", true, myFiles);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw BadRequest.get(e.getMessage());
//        }
//    }
//
//
//    public ResponseEntity<ByteArrayResource> downloadFile(String fileId) {
//        MyFile myFile = findByHashId(fileId);
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(myFile.getContentType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + myFile.getName() + "\"")
//                .body(new ByteArrayResource(myFile.getData()));
//    }
//
//
//    public ResponseEntity<ByteArrayResource> previewFile(String fileId) {
//        MyFile myFile = findByHashId(fileId);
////        System.out.println("previewFile = " + myFile);
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(myFile.getContentType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + myFile.getName() + "\"")
//                .body(new ByteArrayResource(myFile.getData()));
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
//    public MyFile findByHashId(String hashId) {
//        return myFileRepository.findByHashId(hashId).orElseThrow(() -> new ResourceNotFound("MyFile", "hashId", hashId));
//    }
//
//
//    public List<MyFile> findAllByHashId(List<String> hashId) {
//        return myFileRepository.findByHashIdIn(hashId);
//    }
//
//    public Result delete(String hashId) {
//        myFileRepository.delete(findByHashId(hashId));
//        return Result.deleted(!myFileRepository.existsByHashId(hashId));
//    }
//
//    public Result deleteAllByHashId(List<String> hashId) {
//        myFileRepository.deleteAll(findAllByHashId(hashId));
//        return Result.deleted(!myFileRepository.existsByHashId(hashId.get(0)));
//    }
//
//    public Result deleteAll(List<MyFile> hashId) {
//        hashId.get(0);
//        myFileRepository.deleteAll(hashId);
//        return Result.deleted(!myFileRepository.exists(Example.of(hashId.get(0))));
//    }
//
//
//}
