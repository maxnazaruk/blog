package com.luxcampus.blog.service;

import com.luxcampus.blog.entity.Comment;
import com.luxcampus.blog.entity.Post;
import com.luxcampus.blog.repository.CommentRepository;
import com.luxcampus.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Override
    public Comment postComment(String text, Integer postId) {
        Comment comment = Comment.builder()
                .text(text)
                .post(postRepository.findById(postId).get())
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(Integer postId) {
        List<Comment> allComments = commentRepository.findAll();
        List<Comment> comments = new ArrayList<>();

        for (Comment comment : allComments){

            if(comment.getPost().getId() == postId){
                comments.add(comment);
            }
        }


        return commentRepository.findAll();
    }

    @Override
    public Comment getSpecificComment(Integer postId, Integer commentId) {
        return commentRepository.getSpecificComment(postId, commentId);
    }
}
