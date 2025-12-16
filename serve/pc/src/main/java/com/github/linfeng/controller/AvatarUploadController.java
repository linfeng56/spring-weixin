package com.github.linfeng.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class AvatarUploadController {

    private static final List<String> ALLOWED_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");
    private static final long MAX_SIZE = 1024 * 1024;

    @Value("${upload.path:./src/main/resources/static/uploads/avatar}")
    private String uploadPath;

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file) {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Unauthorized"));
        }

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "File is empty"));
        }

        if (file.getSize() > MAX_SIZE) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "File size exceeds 1MB"));
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Only JPG, PNG, GIF files are allowed"));
        }

        try {
            String extension = getExtension(contentType);
            String filename = UUID.randomUUID().toString() + extension;

            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path filePath = uploadDir.resolve(filename);
            file.transferTo(filePath.toFile());

            String avatarUrl = "/uploads/avatar/" + filename;
            return ResponseEntity.ok(Collections.singletonMap("url", avatarUrl));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Failed to upload file"));
        }
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            return auth.getPrincipal().toString();
        }
        return null;
    }

    private String getExtension(String contentType) {
        switch (contentType) {
            case "image/jpeg":
                return ".jpg";
            case "image/png":
                return ".png";
            case "image/gif":
                return ".gif";
            default:
                return ".jpg";
        }
    }
}
