package com.equipo_1.SkyShop.controller;

import com.equipo_1.SkyShop.service.implementations.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/generate-presigned-urls")
    public ResponseEntity<List<URL>> generatePresignedUrls(@RequestParam int count) {
        List<URL> urls = IntStream.range(0, count)
                .mapToObj(i -> s3Service.generatePresignedUrl("uploads/img-" + System.currentTimeMillis() + "-" + i))
                .collect(Collectors.toList());

        return ResponseEntity.ok(urls);
    }
}

