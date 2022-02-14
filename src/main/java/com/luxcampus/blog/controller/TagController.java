package com.luxcampus.blog.controller;

import com.luxcampus.blog.dto.PostWithTagsDto;
import com.luxcampus.blog.dto.PostWithoutCommentsAndStarDto;
import com.luxcampus.blog.entity.Post;
import com.luxcampus.blog.entity.Tag;
import com.luxcampus.blog.service.TagServiceClass;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagServiceClass tagServiceClass;

    @PostMapping
    public Tag saveTag(@RequestBody Tag tag) {
        return tagServiceClass.saveTag(tag);
    }

    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable("id") Integer tagId) {
       tagServiceClass.deleteTag(tagId);
    }

    @GetMapping("/all")
    public List<Tag> findAllTags(){
        return tagServiceClass.getAll();
    }

    @GetMapping("/posts")
    public List<PostWithTagsDto> findAllPostsByTag(@RequestParam("tags") List<String> tags){
        List<Post> postsWithTags =  tagServiceClass.findAllPostsByTags(tags);
        List<PostWithTagsDto> listWithPostAndTags = new ArrayList<>();
        for (Post post : postsWithTags){
            listWithPostAndTags.add(
                    PostWithTagsDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .tags(post.getTags()).build());
        }

        return listWithPostAndTags;
    }

    @PutMapping("/posts")
    public List<PostWithTagsDto> addTagToPosts(@RequestParam("posts") List<Long> posts, @RequestBody Tag tag) {

        List<Post> postsWithNewTag =  tagServiceClass.addTagToPosts(posts, tag);
        List<PostWithTagsDto> listWithPostAndTags = new ArrayList<>();

        for (Post post : postsWithNewTag){
            listWithPostAndTags.add(
                    PostWithTagsDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .tags(post.getTags()).build());
        }

        return listWithPostAndTags;
    }
}
