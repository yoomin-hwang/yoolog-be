package com.webcamp.yoolog.repository;

import com.webcamp.yoolog.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);
}
