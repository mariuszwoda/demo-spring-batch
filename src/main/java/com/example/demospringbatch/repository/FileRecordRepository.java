package com.example.demospringbatch.repository;

import com.example.demospringbatch.entity.FileRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRecordRepository extends JpaRepository<FileRecord, Long> {
}
