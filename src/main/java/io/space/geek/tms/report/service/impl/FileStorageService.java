package io.space.geek.tms.report.service.impl;

import com.mongodb.client.gridfs.model.GridFSFile;
import io.space.geek.tms.commons.exception.ResourceNotFoundException;
import io.space.geek.tms.report.exception.FileStorageException;
import io.space.geek.tms.report.service.IFileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageService implements IFileStorageService {

    private final GridFsTemplate gridFsTemplate;

    private static final String FULL_NAME_TEMPLATE = "%s_%s";

    @Override
    public String uploadFile(MultipartFile file) {
        return uploadFile(file, file.getOriginalFilename());
    }

    @Override
    public String uploadFile(MultipartFile file, String folderName, String fileName) {
        String fullName = getFullFileName(folderName, fileName);
        return uploadFile(file, fullName);
    }

    @Override
    public String uploadFile(MultipartFile file, String fileName) {
        log.info("Upload file '{}' to DB", fileName);
        try (BufferedInputStream is = new BufferedInputStream(file.getInputStream())) {
            gridFsTemplate.store(is, fileName);
        } catch (IOException e) {
            log.error("Error during saving the file '{}' in the DB", fileName, e);
            throw new FileStorageException("Error during saving the file '{}' in DB", fileName, e);
        }
        return fileName;
    }

    @Override
    public void deleteFile(String folderName, String fileName) {
        deleteFile(getFullFileName(folderName, fileName));
    }

    @Override
    public void deleteFile(String fileName) {
        log.info("Remove file '{}' from DB", fileName);
        gridFsTemplate.delete(Query.query(Criteria.where("filename").is(fileName)));
    }

    @Override
    public ResponseEntity<InputStreamResource> getFile(String fileName) {
        log.info("Get file '{}'", fileName);
        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("filename").is(fileName)));
        if (Objects.isNull(file)) {
            throw new ResourceNotFoundException("attachment", "fileName", fileName);
        }
        return ResponseEntity
            .ok()
            .body(new InputStreamResource(gridFsTemplate.getResource(file).getContent()));
    }

    @Override
    public ResponseEntity<InputStreamResource> getFile(String folderName, String fileName) {
        return getFile(getFullFileName(folderName, fileName));
    }

    static String getFullFileName(String folderName, String fileName) {
        return String.format(FULL_NAME_TEMPLATE, folderName, fileName);
    }
}
