package com.team3.blogteam3.comment;

import com.team3.blogteam3.post.Post;
import com.team3.blogteam3.post.PostRepository;
import com.team3.blogteam3.user.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {
    private CommentRepository commentRepository;
    private CommentService commentService;

    private PostRepository postRepository;

    public CommentController(CommentRepository commentRepository, CommentService commentService, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.commentService = commentService;
        this.postRepository = postRepository;
    }

    @PostMapping(value="/post/{postId}/newcomment")
    public String comment(@PathVariable Integer postId,
            @Valid @ModelAttribute("sessionUser") User user,BindingResult result,
                          @ModelAttribute("comment") Comment theComment,BindingResult result2,
                          @ModelAttribute("postToShow") Post postToShow, BindingResult result3) {
        if (result.hasErrors() || result2.hasErrors() || result3.hasErrors()) {
            return "postPage";
        }
        theComment.setUser(user);
        theComment.setPost(postRepository.findById(postId).get());
        theComment.setWrittenAt(LocalDateTime.now());
        commentRepository.save(theComment);
        return "redirect:/post/" + postId;
    }

    @PostMapping(value = "/post/{postId}/comment/delete/{commentId}")
    public String deleteComment(@PathVariable(name = "commentId") Integer commentId,
                                @PathVariable(name = "postId") Integer postId) {
        commentService.deleteById(commentId);
        return "redirect:/post/" + postId;
    }

}
