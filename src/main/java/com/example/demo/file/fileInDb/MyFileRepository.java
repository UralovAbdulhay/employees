package com.example.demo.file.fileInDb;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MyFileRepository extends JpaRepository<MyFile, UUID> {

    void deleteByHashId(String hashId);

//    void deleteAll(List<MyFile> myFiles);

    Optional<MyFile> findByHashId(String hashId);

    List<MyFile> findByHashIdIn(List<String> hashId);

    boolean existsByHashId(String hashId);




}
