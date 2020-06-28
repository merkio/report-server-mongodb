package io.space.geek.tms.report.service;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;

public interface ITestAttachmentService {

    Mono<String> uploadAttachment(MultipartFile file, String runId);

    String uploadAttachment(File file, String folderName, String fileName);

    void deleteAttachment(String runId, String fileName);
}
