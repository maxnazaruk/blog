package com.luxcampus.blog.repository;

import com.luxcampus.blog.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .title("testTitle")
                .content("testComment")
                .build();
    }

    @Test
    public void savePost(){
        postRepository.save(post);
    }

    @Test
    public void printAllPosts(){
        List<Post> posts = postRepository.findAll();
    }

    @Test
    public void findbyTitle(){
       postRepository.findByTitle("testTitle");
    }

    @Test
    public void sortByTitle(){
        postRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
    }
}