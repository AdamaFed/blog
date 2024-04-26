package com.team3.blogteam3.post;

import com.team3.blogteam3.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> findAll();

    Optional<Post> findById(Integer postId);

   // Post create(Post post);

    //Post edit(Post post);

    void deleteById(Integer id);


}
