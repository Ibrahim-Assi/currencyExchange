package com.ex.services.api;

import com.ex.models.entities.FileReference;
import com.ex.models.enums.EnBucketStorageName;
import com.ex.repositories.gl.FileReferenceRepository;
import com.oracle.bmc.model.BmcException;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
import com.oracle.bmc.objectstorage.responses.PutObjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ObjectStorageService {

    private final ObjectStorageClient objectStorageClient;
    private final FileReferenceRepository fileReferenceRepository;

    @Value("${oci.objectstorage.namespace}")
    private String namespace;

    @Value("${oci.objectstorage.bucketName}")
    private String bucketName;

    @SneakyThrows
    @Transactional
    public Long uploadFile(EnBucketStorageName bucketName, MultipartFile file) {
        String objectName = generateUniqueObjectName(file, bucketName.getName());
        PutObjectResponse response = uploadToObjectStorage(bucketName.getName(), objectName, file);

        FileReference fileReference = createFileReference(objectName, response.getETag(), bucketName.getName());
        fileReferenceRepository.save(fileReference);
        return fileReference.getId();
    }

    @Transactional
    public Long replaceFile(EnBucketStorageName bucketName, String objectName, MultipartFile file) {
        Optional<FileReference> existingFileOptional = fileReferenceRepository.findByObjectName(objectName);
        if (existingFileOptional.isPresent()) {
            FileReference existingFile = existingFileOptional.get();
            deleteFileFromStorage(existingFile.getObjectName(), existingFile.getBucketName());
            fileReferenceRepository.delete(existingFile);
        }
        return uploadFile(bucketName, file);
    }

    @Transactional
    public void deleteFile(EnBucketStorageName bucketName, String objectName) {
        Optional<FileReference> fileReferenceOptional = fileReferenceRepository.findByObjectName(objectName);
        if (fileReferenceOptional.isPresent()) {
            FileReference fileReference = fileReferenceOptional.get();
            deleteFileFromStorage(fileReference.getObjectName(), fileReference.getBucketName());
            fileReferenceRepository.delete(fileReference);
        }
    }

    public InputStream downloadFile(EnBucketStorageName bucketName, String objectName) {
        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucketName(bucketName.getName())
                .namespaceName(namespace)
                .objectName(objectName)
                .build();
        GetObjectResponse getResponse = objectStorageClient.getObject(getRequest);
        return getResponse.getInputStream();
    }

    private String generateUniqueObjectName(MultipartFile file, String bucketName) {
        String objectName;
        do {
            objectName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        } while (objectExistsInStorage(bucketName, objectName));
        return objectName;
    }

    private boolean objectExistsInStorage(String bucketName, String objectName) {
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucketName(bucketName)
                    .namespaceName(namespace)
                    .objectName(objectName)
                    .build();
            objectStorageClient.getObject(request); // This will throw if the object doesn't exist
            return true;
        } catch (BmcException e) {
            if (e.getStatusCode() == 404) {
                return false;
            }
            throw e;
        }
    }


    @SneakyThrows
    private PutObjectResponse uploadToObjectStorage(String bucketName, String objectName, MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucketName(bucketName)
                    .namespaceName(namespace)
                    .objectName(objectName)
                    .contentLength(file.getSize())
                    .contentType(file.getContentType())
                    .body$(inputStream)
                    .build();

            return objectStorageClient.putObject(request);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private void deleteFileFromStorage(String objectName, String bucketName) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucketName(bucketName)
                .namespaceName(namespace)
                .objectName(objectName)
                .build();

        objectStorageClient.deleteObject(request);
    }

    private FileReference createFileReference(String objectName, String eTag, String bucketName) {
        FileReference fileReference = new FileReference();
        fileReference.setObjectName(objectName);
        fileReference.setETag(eTag);
        fileReference.setBucketName(bucketName);
        return fileReference;
    }

}
