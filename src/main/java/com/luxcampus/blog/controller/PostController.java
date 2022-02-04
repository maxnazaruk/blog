package com.luxcampus.blog.controller;

import com.luxcampus.blog.dto.PostWithoutCommentsAndStarDto;
import com.luxcampus.blog.dto.PostWithoutCommentsDto;
import com.luxcampus.blog.entity.Post;
import com.luxcampus.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public Post savePost(@RequestBody PostWithoutCommentsAndStarDto postWithoutCommentsAndStarDto) {
        Post post = Post.builder().
                title(postWithoutCommentsAndStarDto.getTitle())
                .content(postWithoutCommentsAndStarDto.getContent()).
                build();
        return postService.savePost(post);
    }

    @GetMapping
    public List<PostWithoutCommentsAndStarDto> getPosts(@RequestParam(value = "title", required = false) String title,
                                                        @RequestParam(value = "sort", required = false) String sort) {

        if (Objects.nonNull(title)) {
            return convertList(postService.getPostsByTitle(title));
        } else if (Objects.nonNull(sort) && sort.equalsIgnoreCase("title")) {
            System.out.println(sort);
            return convertList(postService.sortByTitle());
        }

        return convertList(postService.getPosts());
    }

    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable("id") Integer postId) {
        postService.deletePostById(postId);
    }

    @PutMapping("/{id}")
    public PostWithoutCommentsAndStarDto updatePost(@PathVariable("id") Integer putId, @RequestBody Post post) {
        return convertPost(postService.updatePost(putId, post));
    }

    @GetMapping("/star")
    public List<PostWithoutCommentsDto> getTopPosts() {
        return theSecondListConversion(postService.getTopPosts());
    }

    @PutMapping("/{id}/star")
    public PostWithoutCommentsDto markTopPost(@PathVariable("id") Integer postId) {
        return convertSecondPost(postService.markTopPost(postId));
    }

    @DeleteMapping("/{id}/star")
    public PostWithoutCommentsDto unmarkTopPost(@PathVariable("id") Integer postId) {
        return convertSecondPost(postService.unmarkTopPost(postId));
    }

    @GetMapping("/{id}/full")
    public Post getPostWithAllComments(@PathVariable(name = "id") Integer postId) {
        return postService.getPostWithAllComments(postId);
    }

    private List<PostWithoutCommentsAndStarDto> convertList(List<Post> list) {
        List<PostWithoutCommentsAndStarDto> cutPosts = new ArrayList<>();

        for (Post post : list) {
            cutPosts.add(PostWithoutCommentsAndStarDto.builder().
                    id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .build());
        }
        return cutPosts;
    }

    private PostWithoutCommentsAndStarDto convertPost(Post post) {
        return PostWithoutCommentsAndStarDto.builder().
                id(post.getId())
                .title(post.getTitle())
                .content(post.getContent()).
                build();
    }

    private List<PostWithoutCommentsDto> theSecondListConversion(List<Post> list) {
        List<PostWithoutCommentsDto> cutPosts = new ArrayList<>();

        for (Post post : list) {
            cutPosts.add(PostWithoutCommentsDto.builder().
                    id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .star(post.isStar())
                    .build());
        }
        return cutPosts;
    }

    private PostWithoutCommentsDto convertSecondPost(Post post) {
        return PostWithoutCommentsDto.builder().
                id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .star(post.isStar()).
                build();
    }

}
