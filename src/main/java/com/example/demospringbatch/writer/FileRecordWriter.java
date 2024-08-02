package com.example.demospringbatch.writer;

import com.example.demospringbatch.config.FtpsUtils;
import com.example.demospringbatch.entity.FileRecord;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@StepScope
public class FileRecordWriter implements ItemWriter<FileRecord> {

    @Value("#{jobParameters['sourcePath']}")
    private String sourcePath;

    @Value("#{jobParameters['targetPath']}")
    private String targetPath;

    @Value("#{jobParameters['hostname']}")
    private String hostname;

    @Value("#{jobParameters['username']}")
    private String username;

    @Value("#{jobParameters['password']}")
    private String password;

    private FTPSClient ftpsClient;

    @Autowired
    private FtpsUtils ftpsUtils;

    @PostConstruct
    public void init() throws Exception {
        this.ftpsClient = ftpsUtils.createFTPSClient(hostname, username, password);
    }

    @Override
    public void write(Chunk<? extends FileRecord> items) throws Exception {
        try {
            for (FileRecord item : items) {
                String sourceFilePath = sourcePath + "/" + item.getFileName();
                String targetFolderPath = targetPath + "/" + item.getRecId() + "_" + item.getName() + "_" + item.getDate();
                String targetFilePath = targetFolderPath + "/" + item.getFileName();

                // Ensure target folder exists on FTPS server
                if (!ftpsClient.changeWorkingDirectory(targetFolderPath)) {
                    ftpsClient.makeDirectory(targetFolderPath);
                }

                // Copy file from source to target using FTPS
                copyFileWithFTPS(sourceFilePath, targetFilePath);
            }
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        } finally {
            // Ensure FTPS connection is closed after use
            if (ftpsClient.isConnected()) {
                ftpsClient.logout();
                ftpsClient.disconnect();
            }
        }
    }

    private void copyFileWithFTPS(String sourceFilePath, String targetFilePath) throws Exception {
        try (InputStream inputStream = Files.newInputStream(Paths.get(sourceFilePath))) {
            ftpsClient.storeFile(targetFilePath, inputStream);
        }
    }
}



