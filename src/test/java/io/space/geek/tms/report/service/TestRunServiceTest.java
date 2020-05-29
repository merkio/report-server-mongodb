package io.space.geek.tms.report.service;

import io.space.geek.tms.commons.dto.report.TestRunDTO;
import io.space.geek.tms.report.model.TestRun;
import io.space.geek.tms.report.repository.TestRunRepository;
import io.space.geek.tms.report.service.impl.TestRunService;
import io.space.geek.tms.report.util.EntityAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestRunServiceTest {

    @InjectMocks
    private TestRunService testRunService;

    @Mock
    private TestRunRepository testRunRepository;

    @Mock
    private EntityAdapter entityAdapter;

    private TestRun testRun;
    private TestRunDTO testRunDTO;

    @BeforeEach
    void setUp() {
        testRun = TestRun.builder().id("Id").name("Test Run Name").build();
        testRunDTO = TestRunDTO.builder().id("Id").name("Test Run Name").build();
        lenient().when(entityAdapter.toDTO(testRun)).thenReturn(testRunDTO);
        lenient().when(entityAdapter.fromDTO(testRunDTO)).thenReturn(testRun);
        lenient().when(testRunRepository.findById(anyString())).thenReturn(Mono.just(testRun));
        lenient().when(testRunRepository.deleteById(anyString())).thenReturn(Mono.empty());
        lenient().when(testRunRepository.save(any())).thenReturn(Mono.just(testRun));
    }

    @Test
    void createTestRun() {
        Mono<TestRunDTO> savedTestRun = testRunService.createTestRun(testRunDTO);

        StepVerifier
            .create(savedTestRun)
            .assertNext(testRun -> {
                assertNotNull(testRun);
                assertNotNull(testRun.getId());
                assertNotNull(testRun.getName());
                assertEquals(testRun.getName(), testRunDTO.getName());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void getTestRun() {
        Mono<TestRunDTO> gotTestRun = testRunService.getTestRun(testRun.getId());

        StepVerifier
            .create(gotTestRun)
            .assertNext(testRun -> {
                assertNotNull(testRun);
                assertNotNull(testRun.getId());
                assertNotNull(testRun.getName());
                assertEquals(testRun.getName(), testRunDTO.getName());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void updateTestRun() {
        TestRun updatedTestRun = TestRun.builder().id("Id").name("New name").build();
        TestRunDTO updatedTestRunDTO = TestRunDTO.builder().id("Id").name("New name").build();
        lenient().when(testRunRepository.save(updatedTestRun)).thenReturn(Mono.just(updatedTestRun));
        lenient().when(entityAdapter.fromDTO(updatedTestRunDTO)).thenReturn(updatedTestRun);
        lenient().when(entityAdapter.toDTO(updatedTestRun)).thenReturn(updatedTestRunDTO);

        Mono<TestRunDTO> result = testRunService.updateTestRun(updatedTestRun.getId(), updatedTestRunDTO);
        StepVerifier
            .create(result)
            .assertNext(testRun -> {
                assertNotNull(testRun);
                assertNotNull(testRun.getId());
                assertNotNull(testRun.getName());
                assertEquals(testRun.getName(), updatedTestRun.getName());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void deleteTestRun() {
        testRunService.deleteTestRun(testRun.getId());

        verify(testRunRepository, times(1)).deleteById(anyString());
    }
}