package com.equipo_1.SkyShop.service.implementations;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest.Builder;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest.Builder;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.List;
import java.net.URL;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class S3Service {

    private final S3Presigner s3Presigner;
    private final String bucketName;

    public S3Service(
            @Value("${aws.accessKeyId}") String accessKeyId,
            @Value("${aws.secretAccessKey}") String secretAccessKey,
            @Value("${aws.region}") String region,
            @Value("${aws.s3.bucketName}") String bucketName) {

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        this.s3Presigner = S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
        this.bucketName = bucketName;
    }

    public URL generatePresignedUrl(String objectKey) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(r -> r
                    .signatureDuration(Duration.ofMinutes(10))
                    .putObjectRequest(putObjectRequest)
            );

            return presignedRequest.url();

        } catch (S3Exception e) {
            throw new RuntimeException("Error generating pre-signed URL", e);
        }
    }
}
