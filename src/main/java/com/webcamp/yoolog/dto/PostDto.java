package com.webcamp.yoolog.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> categoryNames;
    private List<FileDto> files;
}