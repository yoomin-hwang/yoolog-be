package com.webcamp.yoolog.service;

import com.webcamp.yoolog.domain.Post;
import com.webcamp.yoolog.domain.Category;
import com.webcamp.yoolog.domain.PostCategory;
import com.webcamp.yoolog.util.FileEntity;
import com.webcamp.yoolog.dto.PostDto;
import com.webcamp.yoolog.dto.FileDto;
import com.webcamp.yoolog.repository.PostRepository;
import com.webcamp.yoolog.repository.CategoryRepository;
import com.webcamp.yoolog.util.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Transactional
    public Long createPost(PostDto postDto, MultipartFile[] files, List<String> categoryNames) throws IOException {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        // 파일 처리
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {  // 빈 파일이 아닌 경우만 처리
                    String fileUrl = fileService.uploadFile(file);
                    FileEntity postFile = new FileEntity();
                    postFile.setFileUrl(fileUrl);
                    postFile.setFileName(file.getOriginalFilename());
                    postFile.setPost(post);
                    post.getFiles().add(postFile);
                }
            }
        }

        // 카테고리 처리
        if (categoryNames != null && !categoryNames.isEmpty()) {
            for (String categoryName : categoryNames) {
                Category category = categoryRepository.findByName(categoryName)
                        .orElseGet(() -> createCategory(categoryName));
                PostCategory postCategory = new PostCategory();
                postCategory.setPost(post);
                postCategory.setCategory(category);
                post.getCategories().add(postCategory);
            }
        }

        postRepository.save(post);
        return post.getId();
    }

    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return convertToDto(post);
    }

    public void updatePost(Long id, PostDto postDto, MultipartFile[] files, List<String> categoryNames) throws IOException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        // 기존 파일 삭제
        if (postDto.getFiles() != null) {
            for (FileDto fileDto : postDto.getFiles()) {
                fileService.deleteFile(fileDto.getId()); // 파일 삭제
                post.getFiles().removeIf(file -> file.getId().equals(fileDto.getId())); // 리스트에서 제거
            }
        }

        // 새로운 파일 업로드
        if (files != null) {
            for (MultipartFile file : files) {
                String fileUrl = fileService.uploadFile(file);
                FileEntity newFile = new FileEntity();
                newFile.setFileUrl(fileUrl);
                newFile.setPost(post);
                post.getFiles().add(newFile);
            }
        }

        // 카테고리 업데이트
        updateCategoriesForPost(post, categoryNames);

        postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private void updatePostFromDto(Post post, PostDto postDto) {
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
    }

    private void updateCategoriesForPost(Post post, List<String> categoryNames) {
        post.getCategories().clear();  // 기존 카테고리 제거

        if (categoryNames != null && !categoryNames.isEmpty()) {
            for (String categoryName : categoryNames) {
                Category category = categoryRepository.findByName(categoryName)
                        .orElseGet(() -> createCategory(categoryName));
                PostCategory postCategory = new PostCategory();
                postCategory.setPost(post);
                postCategory.setCategory(category);
                post.getCategories().add(postCategory);
            }
        }
    }

    private Category createCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        return categoryRepository.save(category);  // 새 카테고리 저장
    }

    private PostDto convertToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        dto.setCategoryNames(post.getCategories().stream()
                .map(pc -> pc.getCategory().getName())
                .collect(Collectors.toList()));

        dto.setFiles(post.getFiles().stream()
                .map(this::convertToFileDto)
                .collect(Collectors.toList()));

        return dto;
    }

    private FileDto convertToFileDto(FileEntity file) {
        FileDto dto = new FileDto();
        dto.setId(file.getId());
        dto.setFileName(file.getFileName());
        dto.setFileUrl(file.getFileUrl());
        return dto;
    }
}