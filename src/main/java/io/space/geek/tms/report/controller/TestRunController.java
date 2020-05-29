package io.space.geek.tms.report.controller;

import io.space.geek.tms.commons.dto.report.TestRunDTO;
import io.space.geek.tms.report.service.ITestRunService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/report/test-run")
public class TestRunController {

    private final ITestRunService testRunService;

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TestRunDTO> createTestRun(TestRunDTO testRunDTO) {
        return testRunService.createTestRun(testRunDTO);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<TestRunDTO> getTestRun(@PathVariable("id") String id) {
        return testRunService.getTestRun(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<TestRunDTO> updateTestRun(@PathVariable("id") String id, TestRunDTO testRunDTO) {
        return testRunService.updateTestRun(id, testRunDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public Mono<Void> deleteTestRun(@PathVariable("id") String id) {
        return testRunService.deleteTestRun(id);
    }
}
