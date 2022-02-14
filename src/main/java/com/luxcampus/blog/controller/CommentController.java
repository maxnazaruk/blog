package com.luxcampus.blog.controller;

import com.luxcampus.blog.entity.Comment;
import com.luxcampus.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}/comments")
    public Comment postComment(@PathVariable("id") Integer postId, @RequestBody String text){
            return commentService.postComment(text, postId);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getComments(@PathVariable("id") Integer postId){
        return commentService.getComments(postId);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public Comment getSpecificComment(@PathVariable("postId") Integer postId, @PathVariable("commentId") Integer commentId){
        return commentService.getSpecificComment(postId, commentId);
    }
}
