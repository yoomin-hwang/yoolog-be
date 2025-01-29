package com.webcamp.yoolog.util;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByPostId(Long postId); // 특정 게시글에 연결된 파일 리스트 조회
}