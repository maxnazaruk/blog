package com.luxcampus.blog.controller;

import com.luxcampus.blog.entity.Comment;
import com.luxcampus.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/api/v1/posts/{id}/comments")
    public Comment postComment(@PathVariable("id") Integer postId, @RequestBody String text){
            return commentService.postComment(text, postId);
    }

    @GetMapping("/api/v1/posts/{id}/comments")
    public List<Comment> getComments(@PathVariable("id") Integer postId){
        return commentService.getComments(postId);
    }

    @GetMapping("/api/v1/posts/{postId}/comments/{commentId}")
    public Comment getSpecificComment(@PathVariable("postId") Integer postId, @PathVariable("commentId") Integer commentId){
        return commentService.getSpecificComment(postId, commentId);
    }
}
