package com.team3.blogteam3.comment;

import com.team3.blogteam3.post.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }

}
