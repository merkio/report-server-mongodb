package io.space.geek.tms.report.model;

import io.qameta.allure.DefaultLaunchResults;
import io.space.geek.tms.commons.domain.BaseDocument;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "test_runs")
@EqualsAndHashCode(callSuper = true)
public class TestRun extends BaseDocument {

    private Long projectId;

    private Long featureId;

    private String name;

    private String description;

    private String tags;

    private Integer status;

    private LocalDateTime started;

    private LocalDateTime finished;

    private Integer duration;

    private String meta;

    private String environment;

    private Boolean manual;

    private String sendTo;


    private Set<DefaultLaunchResults> results;

    @Builder
    private TestRun(String id, LocalDateTime createdOn, LocalDateTime updatedOn, Long projectId,
                   Long featureId, String name, String description, String tags, Integer status,
                   LocalDateTime started, LocalDateTime finished, Integer duration, String meta,
                   String environment, Boolean manual, String sendTo, Set<DefaultLaunchResults> results) {
        super(id, createdOn, updatedOn);
        this.projectId = projectId;
        this.featureId = featureId;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.status = status;
        this.started = started;
        this.finished = finished;
        this.duration = duration;
        this.meta = meta;
        this.environment = environment;
        this.manual = manual;
        this.sendTo = sendTo;
        this.results = results;
    }
}
