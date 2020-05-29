package io.space.geek.tms.report.repository;

import io.space.geek.tms.report.model.TestRun;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRunRepository extends ReactiveMongoRepository<TestRun, String> {

}
