package com.luxcampus.blog.service;

import com.luxcampus.blog.entity.Comment;
import com.luxcampus.blog.entity.Post;

import java.util.List;

public interface CommentService {
    Comment postComment(String text, Integer postId);

    List<Comment> getComments(Integer postId);

    Comment getSpecificComment(Integer postId, Integer commentId);
}
