package com.luxcampus.blog.service;

import com.luxcampus.blog.entity.Post;

import java.util.List;

public interface PostService {
    Post savePost(Post post);

    List<Post> getPosts();

    void deletePostById(Integer postId);

    Post updatePost(Integer putId, Post post);


    List<Post> getPostsByTitle(String title);

    List<Post> sortByTitle();

    List<Post> getTopPosts();

    Post markTopPost(Integer postId);

    Post unmarkTopPost(Integer postId);

    Post getPostWithAllComments(Integer postId);
}
