package io.space.geek.tms.report.repository;

import io.space.geek.tms.report.model.TestRun;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class TestRunRepositoryTest {

    @Autowired
    private TestRunRepository testRunRepository;

    @BeforeEach
    void setUp() {
        testRunRepository.deleteAll();
    }

    @Test
    void addTestRun() {
        Mono<TestRun> testRun = testRunRepository.save(TestRun.builder().build());

        StepVerifier
            .create(testRun)
            .assertNext(Assertions::assertNotNull)
            .expectComplete()
            .verify();
    }
}