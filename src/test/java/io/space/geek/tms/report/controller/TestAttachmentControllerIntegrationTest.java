package io.space.geek.tms.report.controller;


import io.space.geek.tms.report.ReportApplication;
import io.space.geek.tms.report.service.IFileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;

@AutoConfigureWebTestClient
@SpringBootTest(classes = ReportApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestAttachmentControllerIntegrationTest {

    @Value("http://localhost:${local.server.port}/report/attachment")
    private String baseUrl;

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private IFileStorageService fileStorageService;

    @BeforeEach
    void init() {
        gridFsTemplate.delete(Query.query(new Criteria()));
    }

    @Test
    void uploadFile() throws IOException {
        String fileName = "file";
        MockMultipartFile file = new MockMultipartFile("file", fileName, "", "File content".getBytes());
        String folder = "runId";

        var bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", file.getBytes()).filename(fileName);

        //@formatter:off
        testClient
            .post().uri(baseUrl + "/" + folder)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
            .exchange()
            .expectStatus().isCreated();
        //@formatter:on
    }

    @Test
    void getFile() {
        String fileName = "file";
        MockMultipartFile file = new MockMultipartFile("file", fileName, "", "File content".getBytes());
        String folder = "runId";

        String fullName = fileStorageService.uploadFile(file, folder, fileName);

        //@formatter:off
        testClient
            .get().uri(baseUrl + "/" + fullName)
            .exchange()
            .expectStatus().isOk();
        //@formatter:on
    }
}
