package com.luxcampus.blog.repository;

import com.luxcampus.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(
            value = "SELECT * FROM comment WHERE id = :commentId AND post_id = :postId",
            nativeQuery = true)
    Comment getSpecificComment(Integer postId, Integer commentId);
}
