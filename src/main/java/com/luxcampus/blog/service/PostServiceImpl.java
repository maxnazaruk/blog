package com.luxcampus.blog.service;

import com.luxcampus.blog.entity.Post;
import com.luxcampus.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public void deletePostById(Integer postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public Post updatePost(Integer putId, Post post) {
        Post postDB = postRepository.findById(putId).orElse(null);

        if(Objects.nonNull(post.getTitle()) && !"".equalsIgnoreCase(post.getTitle())){
            postDB.setTitle(post.getTitle());
        }

        if(Objects.nonNull(post.getContent()) && !"".equalsIgnoreCase(post.getContent())){
            postDB.setContent(post.getContent());
        }
        return postRepository.save(postDB);
    }

    @Override
    public List<Post> getPostsByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    @Override
    public List<Post> sortByTitle() {
        return postRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
    }

    @Override
    public List<Post> getTopPosts() {
        return postRepository.findByStar(true);
    }

    @Override
    public Post markTopPost(Integer postId) {
        Post postDB = postRepository.findById(postId).get();

        postDB.setStar(true);

        return postRepository.save(postDB);
    }

    @Override
    public Post unmarkTopPost(Integer postId) {
        Post postDB = postRepository.findById(postId).get();

        postDB.setStar(false);

        return postRepository.save(postDB);
    }

    @Override
    public Post getPostWithAllComments(Integer postId) {
        return postRepository.findById(postId).get();
    }


}
