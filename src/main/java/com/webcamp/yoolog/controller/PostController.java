package com.webcamp.yoolog.controller;

import com.webcamp.yoolog.dto.PostDto;
import com.webcamp.yoolog.service.PostService;
import com.webcamp.yoolog.util.FileService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final FileService fileService;

    // create post - 다수의 files, categories 가능
    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody PostDto postDto,
                                           @RequestParam(required = false) MultipartFile[] files,
                                           @RequestParam(required = false) List<String> categoryNames) throws IOException {
        Long postId = postService.createPost(postDto, files, categoryNames);
        return ResponseEntity.ok(postId);
    }

    // get post - files, categories 리스트 포함
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        PostDto postDto = postService.getPost(postId);
        return ResponseEntity.ok(postDto);
    }

    // update post - delete files, categories
    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId,
                                           @RequestBody PostDto postDto,
                                           @RequestParam(required = false) MultipartFile[] files,
                                           @RequestParam(required = false) List<String> categoryNames) throws IOException {
        postService.updatePost(postId, postDto, files, categoryNames);
        return ResponseEntity.ok().build();
    }

    // delete post + 해당 post 의 files, categories 까지
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/posts/{postId}/delete-file/{fileId}")
    public String deleteFile(@PathVariable Long postId, @PathVariable Long fileId) throws FileNotFoundException {
        fileService.deleteFile(fileId);
        return "redirect:/posts/" + postId + "/edit";
    }
}