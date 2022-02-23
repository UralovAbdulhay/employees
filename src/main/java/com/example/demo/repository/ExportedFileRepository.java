package com.example.demo.repository;

import com.example.demo.base.BaseRepository;
import com.example.demo.entity.other.ExportedFile;

import java.util.Optional;


public interface ExportedFileRepository extends BaseRepository<ExportedFile> {

    void deleteAllByIsActive(boolean active);

    Optional<ExportedFile> findByFilePath(String path);

}
