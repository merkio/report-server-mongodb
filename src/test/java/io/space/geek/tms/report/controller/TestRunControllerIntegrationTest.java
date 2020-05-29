package io.space.geek.tms.report.controller;

import io.space.geek.tms.commons.dto.report.TestRunDTO;
import io.space.geek.tms.report.ReportApplication;
import io.space.geek.tms.report.model.TestRun;
import io.space.geek.tms.report.repository.TestRunRepository;
import io.space.geek.tms.report.util.EntityAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.test.StepVerifier;

@AutoConfigureWebTestClient
@SpringBootTest(classes = ReportApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestRunControllerIntegrationTest {

    @Value("http://localhost:${local.server.port}/report/test-run")
    private String baseUrl;

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private TestRunRepository testRunRepository;
    @Autowired
    private EntityAdapter entityAdapter;

    @BeforeEach
    void init() {
        testRunRepository.deleteAll();
    }

    @Test
    void createTestRun() {
        TestRunDTO testRunDTO = TestRunDTO.builder().id("Id").name("Test Run Name").build();

        //@formatter:off
        testClient
            .post().uri(baseUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(testRunDTO))
            .exchange()
            .expectStatus().isCreated();
        //@formatter:on
    }

    @Test
    void updateTestRun() {
        TestRun testRun = TestRun.builder().id("Id").name("Test Run Name").build();

        StepVerifier.create(testRunRepository.save(testRun))
            .expectNextCount(1)
            .expectComplete()
            .verify();

        TestRunDTO testRunDTO = entityAdapter.toDTO(testRun);
        testRunDTO.setName("New Test Run Name");

        //@formatter:off
        testClient
            .put().uri(baseUrl + "/" + testRunDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(testRunDTO))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.name").isNotEmpty();
        //@formatter:on
    }

    @Test
    void deleteTestRun() {
        TestRun testRun = TestRun.builder().id("Id").name("Test Run Name").build();

        StepVerifier.create(testRunRepository.save(testRun))
            .expectNextCount(1)
            .expectComplete()
            .verify();

        //@formatter:off
        testClient
            .delete().uri(baseUrl + "/" + testRun.getId())
            .exchange()
            .expectStatus().isNoContent();
        //@formatter:on

        StepVerifier.create(testRunRepository.findById(testRun.getId()))
            .expectNextCount(0)
            .expectComplete()
            .verify();
    }
}
