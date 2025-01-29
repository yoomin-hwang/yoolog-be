package com.webcamp.yoolog.repository;

import com.webcamp.yoolog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);
    Optional<Post> findById(Long id);
}