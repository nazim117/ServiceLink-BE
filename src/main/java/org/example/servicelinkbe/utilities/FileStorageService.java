package org.example.servicelinkbe.utilities;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FileStorageService {

    @Value("${file.storage.location:local}")
    private String fileStorageLocation;

    @Value("${storage.type}") // Default to 'local' if not set
    private String storageType;

    private final AmazonS3 s3Client;

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    public FileStorageService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String saveImage(MultipartFile file) throws IOException {
        logger.info("saveImage method called with file: {}", file.getOriginalFilename());
        logger.info("File storage location: {}", fileStorageLocation);
        logger.info("Storage type: {}", storageType);
        logger.info("s3client: {}", s3Client);

        if ("s3".equalsIgnoreCase(storageType) && s3Client != null) {
            return saveToS3(file);
        } else {
            return saveToLocal(file);
        }
    }

    private String saveToS3(MultipartFile file) throws IOException {
        logger.info("Starting saveToS3 method...");

        String originalFileName = file.getOriginalFilename();

        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
        String folder = "images/";  // Folder in S3 bucket
        String bucketName = "service-link-fe";
        String s3Key = folder + fileName;
        try (InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, null));
            logger.info("File uploaded successfully to S3.");
        } catch (AmazonServiceException e) {
            logger.error("AmazonServiceException occurred while uploading to S3. Bucket: {}, Key: {}, Error Code: {}, Error Message: {}",
                    bucketName, s3Key, e.getErrorCode(), e.getMessage());
            logger.error("AWS Error Details: ", e);
            throw new IOException("Could not save file to S3. AWS Error: " + e.getMessage(), e);
        } catch (SdkClientException e) {
            logger.error("SdkClientException occurred while uploading to S3. Bucket: {}, Key: {}, Error Message: {}",
                    bucketName, s3Key, e.getMessage());
            logger.error("SDK Client Error Details: ", e);
            throw new IOException("Could not save file to S3. SDK Client Error: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while uploading to S3. Bucket: {}, Key: {}, Error Message: {}",
                    bucketName, s3Key, e.getMessage());
            logger.error("Unexpected Error Details: ", e);
            throw new IOException("Could not save file to S3. Unexpected Error: " + e.getMessage(), e);
        }

        String fileUrl = s3Client.getUrl(bucketName, s3Key).toString();
        logger.info("File available at URL: {}", fileUrl);

        return fileUrl;
    }

    private String saveToLocal(MultipartFile file) throws IOException {
        logger.info("Starting saveToLocal method...");

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        logger.info("Generated unique local file name: {}", fileName);

        File localFile = new File(fileStorageLocation + "/" + fileName);
        logger.info("Saving file locally to: {}", localFile.getAbsolutePath());

        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(localFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            logger.info("File saved locally successfully.");
        } catch (IOException e) {
            logger.error("Failed to save file locally", e);
            throw e;
        }

        return "/images/" + fileName;
    }
}
