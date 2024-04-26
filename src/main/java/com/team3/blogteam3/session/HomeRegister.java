package com.team3.blogteam3.session;

import com.team3.blogteam3.post.Post;
import com.team3.blogteam3.post.PostRepository;
import com.team3.blogteam3.post.PostService;
import com.team3.blogteam3.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class HomeRegister {

    private PostService postService;

    public HomeRegister(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/home")
    public String getHomePage(Model model){
        List<Post> posts = postService.findAll();

        Comparator showNewestFirst = new Comparator<Post>() {
            @Override
            public int compare(Post p1, Post p2) {
                return p2.getWrittenAt().compareTo(p1.getWrittenAt());
            }
        };

        Collections.sort(posts, showNewestFirst);
        List<Post> firstFourPosts = null;
        if(posts.size() > 4){
            firstFourPosts = posts.subList(0,4);
        } else {
            firstFourPosts = posts;
        }

        model.addAttribute("postListHome", firstFourPosts);
        return "home";
    }


}
