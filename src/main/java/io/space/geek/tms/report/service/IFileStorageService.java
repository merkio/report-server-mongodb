package io.space.geek.tms.report.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

    String uploadFile(MultipartFile file);

    String uploadFile(MultipartFile file, String fileName);

    String uploadFile(MultipartFile file, String folderName, String fileName);

    void deleteFile(String fileName);

    void deleteFile(String folderName, String fileName);

    ResponseEntity<InputStreamResource> getFile(String fileName);

    ResponseEntity<InputStreamResource> getFile(String folder, String fileName);
}
