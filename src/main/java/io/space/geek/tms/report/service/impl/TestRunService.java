package io.space.geek.tms.report.service.impl;

import io.space.geek.tms.commons.dto.report.TestRunDTO;
import io.space.geek.tms.commons.util.BeanUtils;
import io.space.geek.tms.report.model.TestRun;
import io.space.geek.tms.report.repository.TestRunRepository;
import io.space.geek.tms.report.service.ITestRunService;
import io.space.geek.tms.report.util.EntityAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestRunService implements ITestRunService {

    private final TestRunRepository testRunRepository;
    private final EntityAdapter entityAdapter;

    @Override
    public Mono<TestRunDTO> createTestRun(TestRunDTO testRunDTO) {
        log.info("Create new test run with name [{}]", testRunDTO.getName());
        return testRunRepository.save(entityAdapter.fromDTO(testRunDTO)).map(entityAdapter::toDTO).single();
    }

    @Override
    public Mono<TestRunDTO> getTestRun(String id) {
        log.info("Get test run by id [{}]", id);
        return testRunRepository.findById(id).map(entityAdapter::toDTO);
    }

    @Override
    public Mono<TestRunDTO> updateTestRun(String id, TestRunDTO testRunDTO) {
        log.info("Update test run [{}]", id);
        return testRunRepository.findById(id).map(currentRun -> {
            TestRun updateTestRun = entityAdapter.fromDTO(testRunDTO);
            BeanUtils.copyNonNullProperties(updateTestRun, currentRun);
            return entityAdapter.toDTO(currentRun);
        }).single();
    }

    @Override
    public Mono<Void> deleteTestRun(String id) {
        log.info("Delete test run [{}]", id);
        return testRunRepository.deleteById(id);
    }
}
