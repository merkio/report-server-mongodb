package io.space.geek.tms.report.service;

import io.space.geek.tms.commons.dto.report.TestRunDTO;
import reactor.core.publisher.Mono;

public interface ITestRunService {

    Mono<TestRunDTO> createTestRun(TestRunDTO testRunDTO);

    Mono<TestRunDTO> getTestRun(String id);

    Mono<TestRunDTO> updateTestRun(String id, TestRunDTO testRunDTO);

    Mono<Void> deleteTestRun(String id);
}
