package com.example.demospringbatch.processor;

import com.example.demospringbatch.entity.FileRecord;
import org.springframework.batch.item.ItemProcessor;

public class FileRecordProcessor implements ItemProcessor<FileRecord, FileRecord> {

    @Override
    public FileRecord process(FileRecord item) throws Exception {
        // Perform any necessary processing here, if needed
        return item;
    }
}
