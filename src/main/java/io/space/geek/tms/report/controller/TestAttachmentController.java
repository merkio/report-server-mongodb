package io.space.geek.tms.report.controller;

import io.space.geek.tms.report.service.IFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class TestAttachmentController {

    private final IFileStorageService fileStorageService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/report/attachment/{runId}",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    void uploadAttachment(@RequestParam("file") MultipartFile file, @PathVariable("runId") String runId) {
        fileStorageService.uploadFile(file, runId, file.getOriginalFilename());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/report/attachment/{folderName}/{fileName}",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    String uploadAttachment(@RequestParam("file") MultipartFile file, @PathVariable("folderName") String folderName, @PathVariable("fileName") String fileName) {
        return fileStorageService.uploadFile(file, folderName, fileName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/report/attachment/{folderName}/{fileName}")
    ResponseEntity<InputStreamResource> getAttachment(@PathVariable("folderName") String folderName, @PathVariable("fileName") String fileName) {
        return fileStorageService.getFile(folderName, fileName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/report/attachment/{fileName}")
    ResponseEntity<InputStreamResource> getAttachment(@PathVariable("fileName") String fileName) {
        return fileStorageService.getFile(fileName);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/report/attachment/{runId}/{fileName}")
    void deleteAttachment(@PathVariable("runId") String runId, @PathVariable("fileName") String fileName) {
        fileStorageService.deleteFile(runId, fileName);
    }
}
