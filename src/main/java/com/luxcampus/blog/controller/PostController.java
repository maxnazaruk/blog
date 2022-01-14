package com.luxcampus.blog.controller;

import com.luxcampus.blog.entity.Post;
import com.luxcampus.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/api/v1/posts")
    public Post savePost(@RequestBody Post post) {
        return postService.savePost(post);
    }

    @GetMapping("/api/v1/posts")
    public List<Post> getPosts(@RequestParam(value = "title", required = false) String title,
                               @RequestParam(value = "sort", required = false) String sort) {


        if (Objects.nonNull(title)) {
            return postService.getPostsByTitle(title);
        } else if (Objects.nonNull(sort) && sort.equalsIgnoreCase("title")) {
            System.out.println(sort);
            return postService.sortByTitle();
        }
        return postService.getPosts();
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public String deletePostById(@PathVariable("id") Integer postId) {
        postService.deletePostById(postId);
        return "Post deleted successfully!";
    }

    @PutMapping("/api/v1/posts/{id}")
    public Post updatePost(@PathVariable("id") Integer putId, @RequestBody Post post) {
        return postService.updatePost(putId, post);
    }

    @GetMapping("/api/v1/posts/star")
    public List<Post> getTopPosts() {
        return postService.getTopPosts();
    }

    @PutMapping("/api/v1/posts/{id}/star")
    public Post markTopPost(@PathVariable("id") Integer postId) {
        return postService.markTopPost(postId);
    }

    @DeleteMapping("/api/v1/posts/{id}/star")
    public Post unmarkTopPost(@PathVariable("id") Integer postId) {
        return postService.unmarkTopPost(postId);
    }

    @GetMapping("/api/v1/posts/{id}/full")
    public Post getPostWithAllComments(@PathVariable(name = "id") Integer postId) {
        return postService.getPostWithAllComments(postId);
    }
}
