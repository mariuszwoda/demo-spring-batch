package com.example.demospringbatch.config;

import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;

@Configuration
public class FtpsUtils {

    @Value("${ftps.cert.path}")
    private Resource certPath;

    @Value("${ftps.cert.password}")
    private String certPassword;

    private SSLContext sslContext;

    @PostConstruct
    public void init() throws Exception {
        // Load the certificate
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (InputStream keyStoreStream = certPath.getInputStream()) {
            keyStore.load(keyStoreStream, certPassword.toCharArray());
        }

        // Initialize KeyManagerFactory with the loaded keystore
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, certPassword.toCharArray());

        // Initialize TrustManagerFactory with the loaded keystore
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        // Initialize SSLContext with the KeyManagerFactory and TrustManagerFactory
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
    }

    public FTPSClient createFTPSClient(String hostname, String username, String password) throws Exception {
        // Create FTPSClient with SSL context
        FTPSClient ftpsClient = new FTPSClient();
        ftpsClient.setSocketFactory(sslContext.getSocketFactory());
        ftpsClient.connect(hostname);
        ftpsClient.login(username, password);
        ftpsClient.execPROT("P"); // Set data channel protection to private

        return ftpsClient;
    }
}

