package com.claimsprocessingplatform.processingplatform.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class AzureBlobStorageService {

    @Autowired
    private BlobContainerClient blobContainerClient;

    /**
     * Uploads a file to Azure Blob Storage.
     *
     * @param file The multipart file to upload.
     * @param fileName The desired name for the blob.
     * @return The URL of the uploaded blob.
     * @throws IOException If an I/O error occurs during upload.
     */
    public String uploadFile(MultipartFile file, String fileName) throws IOException {

        // Get a reference to the blob
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

        // Upload the file stream
        try (InputStream dataStream = file.getInputStream()) {
            // The 'true' argument overwrites the blob if it already exists
            blobClient.upload(dataStream, file.getSize(), true);
        }

        // Return the URL of the uploaded blob
        return blobClient.getBlobUrl();
    }
}
