package com.team3.blogteam3.comment;

import java.util.List;


public interface CommentService {
    List<Comment> findAll();

    void deleteById(Integer id);

}
