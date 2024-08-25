package org.example.servicelinkbe.utilities;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class FileStorageService {

    @Value("${file.storage.location}")
    private String fileStorageLocation;

    @Value("${storage.type:local}") // Default to 'local' if not set
    private String storageType;

    private AmazonS3 s3Client;
    private final String bucketName = "service-link-fe";

    public FileStorageService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String saveImage(MultipartFile file) throws IOException {
        if ("s3".equalsIgnoreCase(storageType) && s3Client != null) {
            return saveToS3(file);
        } else {
            return saveToLocal(file);
        }
    }

    private String saveToS3(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, null));
        }
        return s3Client.getUrl(bucketName, fileName).toString();
    }

    private String saveToLocal(MultipartFile file) throws IOException {
        // Generate a unique filename
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Create the full file path
        File localFile = new File(fileStorageLocation + "/" + fileName);

        // Ensure the directory exists
        localFile.getParentFile().mkdirs();

        // Save the file to the specified location
        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(localFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        // Return the relative URL for accessing the file
        return "/images/" + fileName;
    }

}