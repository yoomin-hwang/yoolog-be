package com.webcamp.yoolog.controller;

import com.webcamp.yoolog.dto.CategoryDto;
import com.webcamp.yoolog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // Create new category
    @PostMapping
    public ResponseEntity<Long> createCategory(@RequestBody CategoryDto categoryDto) {
        Long categoryId = categoryService.createCategory(categoryDto);
        return ResponseEntity.ok(categoryId);
    }

    // Get all categories
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Delete a category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }
}
