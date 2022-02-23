package com.example.demo.service.impl;

import com.example.demo.entity.other.ExportedFile;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.repository.ExportedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExportedFileService {
    private final ExportedFileRepository exportedFileRepository;

    public ExportedFile save(ExportedFile exportedFile) {
        return exportedFileRepository.save(exportedFile);
    }

    public boolean delete(Long id) {
        exportedFileRepository.deleteById(id);
        return !exportedFileRepository.existsById(id);
    }

    public ExportedFile findById(Long id) {
        return exportedFileRepository.findById(id).orElseThrow(() -> ResourceNotFound.get("ExportedFile", "id", id));
    }

    public void deleteAllByActivation() {
        exportedFileRepository.deleteAllByIsActive(false);
    }

    public boolean setDisActive(Long id) {
        ExportedFile file = findById(id);
        file.setActive(false);
        return exportedFileRepository.save(file) != null;
    }


    public boolean setDisActive(String path) {
        ExportedFile file = exportedFileRepository.findByFilePath(path).orElseThrow(() -> ResourceNotFound.get("ExportedFile", "path", path));
        file.setActive(false);
        return exportedFileRepository.save(file) != null;
    }


}
