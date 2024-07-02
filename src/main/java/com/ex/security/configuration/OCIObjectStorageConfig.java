package com.ex.security.configuration;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

@Configuration
public class OCIObjectStorageConfig {

    @Value("${oci.objectstorage.namespace}")
    private String namespace;

    @Value("${oci.objectstorage.tenancyId}")
    private String tenancyId;

    @Value("${oci.objectstorage.userId}")
    private String userId;

    @Value("${oci.objectstorage.fingerprint}")
    private String fingerprint;

    @Value("${oci.objectstorage.pemFilePath}")
    private String pemFilePath;

    @Bean
    public ObjectStorageClient objectStorageClient() throws IOException {
        AuthenticationDetailsProvider authDetailsProvider =
                SimpleAuthenticationDetailsProvider.builder()
                        .tenantId(tenancyId)
                        .userId(userId)
                        .fingerprint(fingerprint)
                        .privateKeySupplier(() -> {
                            try {
                                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(pemFilePath);
                                if (inputStream == null) {
                                    throw new RuntimeException("Failed to load PEM file from resources");
                                }
                                return inputStream;
                            } catch (RuntimeException e) {
                                throw e;
                            } catch (Exception e) {
                                throw new RuntimeException("Failed to read PEM file", e);
                            }
                        })
                        .build();

        ObjectStorageClient client = new ObjectStorageClient(authDetailsProvider);
        client.setRegion(Region.UK_LONDON_1);

        return client;
    }
}
