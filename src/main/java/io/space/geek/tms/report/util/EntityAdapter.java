package io.space.geek.tms.report.util;

import io.space.geek.tms.commons.dto.report.TestRunDTO;
import io.space.geek.tms.commons.util.BeanUtils;
import io.space.geek.tms.report.model.TestRun;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntityAdapter {

    private final ModelMapper modelMapper;

    @NotNull
    public TestRunDTO toDTO(@NotNull TestRun testRun) {
        TestRunDTO testRunDTO = TestRunDTO.builder().build();
        BeanUtils.copyNonNullProperties(testRun, testRunDTO);
        return testRunDTO;
    }

    @NotNull
    public TestRun fromDTO(@NotNull TestRunDTO testRunDTO) {
        TestRun testRun = TestRun.builder().build();
        BeanUtils.copyNonNullProperties(testRunDTO, testRun);
        return testRun;
    }
}
