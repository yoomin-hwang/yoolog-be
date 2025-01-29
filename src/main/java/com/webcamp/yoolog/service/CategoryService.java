package com.webcamp.yoolog.service;

import com.webcamp.yoolog.domain.Category;
import com.webcamp.yoolog.dto.CategoryDto;
import com.webcamp.yoolog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // 모든 카테고리 조회
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 카테고리 생성
    @Transactional
    public Long createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return category.getId();
    }

    // 카테고리 조회
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return convertToDto(category);
    }

    // 카테고리 이름으로 조회
    public Category findByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + name));
    }

    // 카테고리 DTO로 변환
    private CategoryDto convertToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        // 카테고리 찾기
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // 관련된 게시물들도 처리할 수 있다면 삭제하거나 카테고리에서 제거
        categoryRepository.delete(category);
    }

}
