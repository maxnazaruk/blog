package com.luxcampus.blog.service;

import com.luxcampus.blog.entity.Post;
import com.luxcampus.blog.entity.Tag;
import com.luxcampus.blog.repository.PostRepository;
import com.luxcampus.blog.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceClass implements TagService{
    private TagRepository tagRepository;
    private PostRepository postRepository;

    public TagServiceClass(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag saveTag(Tag tag){
        return tagRepository.save(tag);
    }

    public void deleteTag(long id){
        tagRepository.deleteById(id);
    }

    public List<Tag> getAll(){
        return tagRepository.findAll();
    }

    public List<Post> findAllPostsByTag(Tag tag){
        return postRepository.findAll();
    }

    public List<Post> addTagToPosts(List<Long> posts_ids, Tag tag){

        List<Post> posts = postRepository.findAll();

        for (Long id : posts_ids){
            for (Post post : posts){
                if(id.equals(post.getId())){
                    post.getTags().add(tag);
                }
            }
        }

        return posts;
    }

    public List<Post> findAllPostsByTags(List<String> tags){
        List<Post> posts = postRepository.findAll();
        List<Post> allPostsByTags = new ArrayList<>();

        for (Post post : posts){
            for (String tagName : tags){
                if(isTagPresent(post, tagName)){
                    allPostsByTags.add(post);
                }
            }
        }

        return allPostsByTags;
    }

    private boolean isTagPresent(Post post, String tagName){
        for (Tag tag : post.getTags()){
            if(tag.getTag().equals(tagName)){
                return true;
            }
        }
        return false;
    }
}
