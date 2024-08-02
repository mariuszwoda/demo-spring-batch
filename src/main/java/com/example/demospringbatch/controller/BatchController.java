package com.example.demospringbatch.controller;

import com.example.demospringbatch.dto.BatchJobParameters;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/batch")
@Tag(name = "Batch Job Controller", description = "APIs for managing batch jobs")
@Slf4j
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @PostMapping("/run")
    @Operation(summary = "Run Batch Job", description = "Starts the batch job with the given parameters")
    public ResponseEntity<String> runBatchJob(@RequestBody BatchJobParameters params) {

        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("sourcePath", params.getSourcePath())
                    .addString("targetPath", params.getTargetPath())
                    .addString("hostname", params.getHostname())
                    .addString("username", params.getUsername())
                    .addString("password", params.getPassword())
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
            log.info("Batch job started successfully.");
            return ResponseEntity.ok("Batch job started successfully.");
        } catch (Exception e) {
            log.error("Failed to start batch job.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start batch job.");
        }
    }
}
