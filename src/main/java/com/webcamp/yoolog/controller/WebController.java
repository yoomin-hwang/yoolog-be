package com.webcamp.yoolog.controller;

import com.webcamp.yoolog.dto.PostDto;
import com.webcamp.yoolog.service.CategoryService;
import com.webcamp.yoolog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "home";
    }

    // New post creation form
    @GetMapping("/posts/new")
    public String createPostForm(Model model) {
        model.addAttribute("post", new PostDto());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("content", "post-form");
        return "home";
    }

    // Create post
    @PostMapping("/posts/new")
    public String createPost(@ModelAttribute PostDto postDto,
                             @RequestParam(required = false) MultipartFile[] files,
                             @RequestParam(required = false) List<String> categoryNames) throws IOException {
        Long postId = postService.createPost(postDto, files, categoryNames);
        return "redirect:/posts/" + postId;
    }

    // View a specific post
    @GetMapping("/posts/{postId}")
    public String viewPost(@PathVariable Long postId, Model model) {
        model.addAttribute("post", postService.getPost(postId));
        model.addAttribute("content", "post-view");
        return "home";
    }

    @GetMapping("/posts/{postId}/edit")
    public String editPostForm(@PathVariable Long postId, Model model) {
        model.addAttribute("post", postService.getPost(postId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("content", "post-edit");
        return "home";
    }

    // 수정 처리 - GetMapping을 PostMapping으로 변경
    @PostMapping("/posts/{postId}")  // URL 패턴도 변경
    public String updatePost(@PathVariable Long postId,
                             @ModelAttribute PostDto postDto,
                             @RequestParam(required = false) MultipartFile[] files,
                             @RequestParam(required = false) List<String> categoryNames) throws IOException {
        postService.updatePost(postId, postDto, files, categoryNames);
        return "redirect:/posts/" + postId;
    }

    // Delete post
    @PostMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/";
    }
}
