package com.luxcampus.blog.service;

import com.luxcampus.blog.entity.Post;
import com.luxcampus.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        Post testPost = Post.builder()
                .title("test")
                .content("content")
                .build();


        List<Post> posts = new ArrayList<>();
        posts.add(testPost);

        Post oldPost = Post.builder()
                .title("old")
                .content("old")
                .build();

        Post updatedPost = Post.builder()
                .title("new")
                .content("new")
                .build();

        Post zPost = Post.builder()
                .title("z")
                .content("content")
                .build();

        Post aPost = Post.builder()
                .title("a")
                .content("content")
                .build();

        List<Post> sort = new ArrayList<>();
        sort.add(aPost);
        sort.add(zPost);

        Post topPost_a = Post.builder()
                .title("a")
                .content("content")
                .star(true)
                .build();

        Post nonTopPost = Post.builder()
                .title("a")
                .content("content")
                .star(false)
                .build();

        Post marketTopPost = Post.builder()
                .title("a")
                .content("content")
                .star(true)
                .build();

        List<Post> topPosts = new ArrayList<>();
        topPosts.add(topPost_a);

        List<Post> markedTop = new ArrayList<>();
        markedTop.add(marketTopPost);

        Optional<Post> old = Optional.ofNullable(testPost);


        Mockito.when(postRepository.save(testPost)).thenReturn(testPost);
        Mockito.when(postRepository.save(oldPost)).thenReturn(oldPost);
        Mockito.when(postRepository.save(updatedPost)).thenReturn(updatedPost);
        Mockito.when(postRepository.save(nonTopPost)).thenReturn(nonTopPost);
        Mockito.when(postRepository.save(marketTopPost)).thenReturn(marketTopPost);

        Mockito.when(postRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))).thenReturn(sort);
        Mockito.when(postRepository.findAll()).thenReturn(posts);

        Mockito.when(postRepository.findByTitle("test")).thenReturn(posts);
        Mockito.when(postRepository.findByTitle("a")).thenReturn(markedTop);
        Mockito.when(postRepository.findByStar(true)).thenReturn(topPosts);
        Mockito.when(postRepository.findById(1)).thenReturn(old);
        Mockito.when(postRepository.save(testPost)).thenReturn(testPost);

    }

    @Test
    void savePost() {
        Post testPost = Post.builder()
                .title("test")
                .content("content")
                .build();

        Post savePost = postService.savePost(testPost);
        assertEquals(savePost, testPost);
    }

    @Test
    void getPosts() {
        Post testPost = Post.builder()
                .title("test")
                .content("content")
                .build();

        List<Post> posts = new ArrayList<>();
        posts.add(testPost);

        assertEquals(posts, postService.getPosts());
    }

    @Test
    void deletePostById() {
        postService.deletePostById(1);
        verify(postRepository, times(1)).deleteById(1);
    }

    @Test
    void updatePost() {
        Post testPost = Post.builder()
                .title("test")
                .content("content")
                .build();

        assertEquals(testPost, postRepository.findById(1).get());
        assertEquals(testPost, postRepository.save(testPost));
    }

    @Test
    void getPostsByTitle() {
        Post testPost = Post.builder()
                .title("test")
                .content("content")
                .build();

        List<Post> testPosts = new ArrayList<>();
        testPosts.add(testPost);

        String title = "test";
        List<Post> resultPosts = postService.getPostsByTitle(title);

        assertEquals(testPosts, resultPosts);
    }

    @Test
    void sortByTitle() {
        Post zPost = Post.builder()
                .title("z")
                .content("content")
                .build();

        Post aPost = Post.builder()
                .title("a")
                .content("content")
                .build();

        List<Post> sort = new ArrayList<>();
        sort.add(zPost);
        sort.add(aPost);

        sort.sort(new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });

        assertEquals(sort, postService.sortByTitle());
    }

    @Test
    void getTopPosts() {
        Post topPost = Post.builder()
                .title("a")
                .content("content")
                .star(true)
                .build();
        List<Post> topPosts = new ArrayList<>();
        topPosts.add(topPost);
        assertEquals(topPosts, postService.getTopPosts());
    }

    @Test
    void markTopPost() {
        Post nonTopPost = Post.builder()
                .title("a")
                .content("content")
                .star(false)
                .build();

        assertEquals(nonTopPost, postRepository.save(nonTopPost));
        nonTopPost.setStar(true);
        List<Post> topPosts = new ArrayList<>();
        topPosts.add(nonTopPost);
        assertEquals(topPosts, postRepository.findByTitle("a"));
    }

    @Test
    void unmarkTopPost() {

    }

    @Test
    void getPostWithAllComments() {
    }
}