package com.ecommerce.batch_service.importUsers;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ImportUsersJobConfig {

    @Bean
    public Job importUsersJob(JobRepository jobRepository, Step importUsersStep) {
        return new JobBuilder("importUsersJob", jobRepository)
                .start(importUsersStep)
                .build();
    }

    @Bean
    public Step importUsersStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<UserCsv> userCsvReader,
            JdbcTemplate jdbcTemplate
    ) {
        return new StepBuilder("importUsersStep", jobRepository)
                .<UserCsv, UserCsv>chunk(50, transactionManager)
                .reader(userCsvReader)
                .processor(new UserItemProcessor())
                .writer(new UserItemWriter(jdbcTemplate))
                .build();
    }

    @Bean
    public FlatFileItemReader<UserCsv> userCsvReader() {
        return new FlatFileItemReaderBuilder<UserCsv>()
                .name("userCsvReader")
                .resource(new ClassPathResource("data/users.csv"))
                .linesToSkip(1)
                .delimited()
                .names("email", "name")
                .targetType(UserCsv.class)
                .build();
    }
}
