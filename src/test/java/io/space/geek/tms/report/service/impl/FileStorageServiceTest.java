package io.space.geek.tms.report.service.impl;

import com.mongodb.client.gridfs.model.GridFSFile;
import io.space.geek.tms.report.service.IFileStorageService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
public class FileStorageServiceTest {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    private IFileStorageService fileStorageService;

    @BeforeEach
    void setUp() {
        gridFsTemplate.delete(Query.query(new Criteria()));

        fileStorageService = new FileStorageService(gridFsTemplate);
    }

    @Test
    void uploadFile() {
        MockMultipartFile file = new MockMultipartFile("file", "file", "","File content".getBytes());

        String fileName = fileStorageService.uploadFile(file);
        ResponseEntity<InputStreamResource> savedFile = fileStorageService.getFile(file.getOriginalFilename());

        assertEquals(file.getName(), fileName);
        assertEquals(savedFile.getStatusCode(), HttpStatus.OK);
        assertTrue(savedFile.hasBody());
    }

    @Test
    void uploadFileWithFolder() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "fileName", "","File content".getBytes());

        String folder = "folder";
        String fileName = fileStorageService.uploadFile(file, folder, file.getOriginalFilename());
        String fullName = FileStorageService.getFullFileName(folder, file.getOriginalFilename());
        ResponseEntity<InputStreamResource> savedFile = fileStorageService.getFile(fullName);

        assertEquals(fullName, fileName);
        assertEquals(savedFile.getStatusCode(), HttpStatus.OK);
        assertTrue(savedFile.hasBody());
        assertEquals(file.getSize(), Objects.requireNonNull(savedFile.getBody()).contentLength());
    }
}
