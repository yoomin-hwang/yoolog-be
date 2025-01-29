package com.webcamp.yoolog.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final FileRepository fileRepository;

    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Empty file");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;

        // 디스크에 파일 저장 (java.nio.file.Path 사용)
        Path targetPath = Paths.get(uploadDir + "/" + newFilename);

        Files.createDirectories(targetPath.getParent()); // 디렉토리 생성
        file.transferTo(targetPath.toFile()); // 파일 저장

        // 파일 정보를 데이터베이스에 저장
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileUrl(newFilename);  // 저장된 파일 이름을 URL로 설정
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