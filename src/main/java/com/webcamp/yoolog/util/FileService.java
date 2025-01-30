package com.webcamp.yoolog.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final FileRepository fileRepository;

    @PostConstruct
    public void init() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Empty file");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;

        // 절대 경로 생성
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        // 디렉토리가 없으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 파일 저장 경로 생성
        Path targetPath = uploadPath.resolve(newFilename);

        // 파일 저장
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        // 파일 정보를 데이터베이스에 저장
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(originalFilename);
        fileEntity.setFileUrl("/uploads/" + newFilename);  // URL은 상대 경로로 저장
        fileRepository.save(fileEntity);  // 파일 엔티티를 DB에 저장

        return "/uploads/" + newFilename;  // 업로드된 파일의 상대 경로 반환
    }

    public void uploadFiles(MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            uploadFile(file);  // 각 파일을 업로드
        }
    }

    public void deleteFile(Long fileId) throws FileNotFoundException {
        // fileId로 FileEntity 엔티티를 찾아서 해당 파일 삭제
        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("File not found with ID: " + fileId));

        // 파일 시스템에서 파일 삭제 (java.nio.file.Path 사용)
        Path fileToDelete = Paths.get(uploadDir + "/" + file.getFileUrl());
        try {
            Files.deleteIfExists(fileToDelete); // 파일 삭제
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete the file", e);
        }

        // DB에서 파일 삭제
        fileRepository.delete(file);
    }
}