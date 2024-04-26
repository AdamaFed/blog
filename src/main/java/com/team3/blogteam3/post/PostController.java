package com.team3.blogteam3.post;

import com.team3.blogteam3.comment.Comment;
import com.team3.blogteam3.comment.CommentService;
import com.team3.blogteam3.session.SessionRepository;
import com.team3.blogteam3.user.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class PostController {

    private PostRepository postRepository;
    private PostService postService;

    private SessionRepository sessionRepository;

    private CommentService commentService;

    public PostController(PostRepository postRepository, PostService postService, SessionRepository sessionRepository, CommentService commentService) {
        this.postRepository = postRepository;
        this.postService = postService;
        this.sessionRepository = sessionRepository;
        this.commentService = commentService;
    }

    @GetMapping(value = "/showAllPosts")
    public String showAllPosts(Model model) {

        List<Post> posts = postService.findAll();
        Comparator showNewestFirst = new Comparator<Post>() {
            @Override
            public int compare(Post p1, Post p2) {
                return p2.getWrittenAt().compareTo(p1.getWrittenAt());
            }
        };
        Collections.sort(posts, showNewestFirst);
        model.addAttribute("postList", posts);
        return "allPosts";
    }

    @GetMapping(value = "/post/{id}")
    public String showThePost(@PathVariable(name = "id") Integer id , Model model){
        Post postToShow = postRepository.findById(id).orElseThrow();
        Comment comment = new Comment();
        model.addAttribute("comment", comment);
        model.addAttribute("postToShow", postToShow);
        model.addAttribute("postId", "/post/" + id + "/newcomment");

        List<Comment> allComments = commentService.findAll();
        List<Comment> filteredComments = allComments.stream().filter(commentEl -> commentEl.getPost().getId() == id).toList();
        model.addAttribute("commentList", filteredComments);

        return "postPage";
    }

    @GetMapping(value = "/newpost")
    public String showCreatePostForm( Model model){
        Post post = new Post();
        model.addAttribute("post", post);
        return "newPost";
    }

    @PostMapping(value="/newpost")
    public String createPost (@Valid @ModelAttribute("sessionUser") User user, @ModelAttribute("post") Post thePost,  BindingResult result) {
        if (result.hasErrors()) {
            return "newPost";
        }
        thePost.setUser(user);
        thePost.setWrittenAt(LocalDateTime.now());
        postRepository.save(thePost);
        return "redirect:/home";
    }

    @GetMapping(value = "/post/edit/{id}")
    public String editPost(@PathVariable(name = "id") Integer id, Model model){
        Post postToEdit = postRepository.findById(id).orElseThrow();
        model.addAttribute("postToEdit", postToEdit);
        return "editPost";
    }

    @PostMapping(value="/post/edit/{id}")
    public String editPost (@ModelAttribute("postToEdit") Post thePost, BindingResult result) {
        if (result.hasErrors()) {
            return "editPost";
        }
        postRepository.save(thePost);
        return "redirect:/post/" + thePost.getId();
    }

    @GetMapping(value = "/post/delete/{id}")
    public String getPostToDelete(Model model, @PathVariable(name = "id") Integer id) {
        Post postToDelete = postRepository.findById(id).orElseThrow();
        model.addAttribute("postToDelete", postToDelete);
        return "deletePost";
    }
    @PostMapping(value = "/post/delete/{id}")
    public String delete(@ModelAttribute(name = "postToDelete") Post thePost) {
        postService.deleteById(thePost.getId());
        return "redirect:/showAllPosts";
    }
}
