package com.team3.blogteam3.post;

import com.team3.blogteam3.comment.Comment;
import com.team3.blogteam3.comment.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;


    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired


    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(Integer postId) {
        return postRepository.findById(postId);
    }

    /*@Override
    public Post edit(Post post) {
        return null;
    } in Controller*/

    @Override
    public void deleteById(Integer id) {
        postRepository.deleteById(id);
    }




}
