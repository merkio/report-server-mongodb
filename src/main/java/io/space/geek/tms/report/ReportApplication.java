package io.space.geek.tms.report;

import io.space.geek.tms.commons.config.FeignClientsConfiguration;
import io.space.geek.tms.commons.config.ModelMapperConfiguration;
import io.space.geek.tms.commons.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Import({
    ModelMapperConfiguration.class,
    FeignClientsConfiguration.class,
    SwaggerConfiguration.class
})
@EnableMongoAuditing
@EnableConfigurationProperties
@EnableReactiveMongoRepositories
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportApplication.class, args);
    }

}
