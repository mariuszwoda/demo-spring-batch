package com.example.demospringbatch.entity;

//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "file_table")
public class FileRecord {
    @Id
    private Long recId;
    private String name;
    private String date;
    private String fileName;
}
