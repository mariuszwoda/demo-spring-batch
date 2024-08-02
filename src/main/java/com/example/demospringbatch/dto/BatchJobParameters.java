package com.example.demospringbatch.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BatchJobParameters {

    @Schema(description = "Source path", example = "\\default\\source\\tpath")
    private String sourcePath;

    @Schema(description = "Target path", example = "\\default\\target\\path")
    private String targetPath;

//    https://stackoverflow.com/questions/7169845/using-python-how-can-i-access-a-shared-folder-on-windows-network
//    @Schema(description = "Hostname", example = "ftp://speedtest.tele2.net")
//    @Schema(description = "Hostname", example = "\\\\speedtest.tele2.net\\share\\path\\to\\file")
    @Schema(description = "Hostname", example = "\\\\speedtest.tele2.net")
//    @Schema(description = "Hostname", example = "ftp.example.com")
//    private String hostname = "ftp.example.com";
    private String hostname;

    @Schema(description = "Username", example = "anonymous")
    private String username;

    @Schema(description = "Password", example = "")
    private String password;
}
