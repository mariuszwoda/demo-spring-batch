package com.example.demospringbatch.config;

import com.example.demospringbatch.entity.FileRecord;
import com.example.demospringbatch.processor.FileRecordProcessor;
import com.example.demospringbatch.repository.FileRecordRepository;
import com.example.demospringbatch.writer.FileRecordWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private FileRecordRepository fileRecordRepository;

    @Bean
    public Job fileProcessingJob(Step fileProcessingStep) {
        return new JobBuilder("fileProcessingJob", jobRepository)
                .start(fileProcessingStep)
                .build();
    }

    @Bean
    public Step fileProcessingStep(ItemReader<FileRecord> reader,
                                   ItemProcessor<FileRecord, FileRecord> processor,
                                   ItemWriter<FileRecord> writer) {
        return new StepBuilder("fileProcessingStep", jobRepository)
                .<FileRecord, FileRecord>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public RepositoryItemReader<FileRecord> reader() {
        return new RepositoryItemReaderBuilder<FileRecord>()
                .repository(fileRecordRepository)
                .methodName("findAll")
                .sorts(Collections.singletonMap("recId", Sort.Direction.ASC))
                .saveState(true)
                .name("fileRecordReader")
                .build();
    }

    @Bean
    public ItemProcessor<FileRecord, FileRecord> processor() {
        return new FileRecordProcessor();
    }

    @Bean
    @StepScope
    public ItemWriter<FileRecord> writer() {
        return new FileRecordWriter();
    }
}




