package com.team3.blogteam3.admin;

import com.team3.blogteam3.user.User;
import com.team3.blogteam3.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminUserController {
    private final UserRepository userRepository;

    public AdminUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin/users")
    public String viewUsers(@ModelAttribute("sessionUser") User user, Model model) {
        if (user != null && "ADMIN".equals(user.getRole())) {
            List<User> userList = userRepository.findAll();
            model.addAttribute("users", userList);
            return "admin/user-list";
       } else {
           return "redirect:/home";
        }
    }

    @PostMapping("/admin/promote")
    public String promoteToAdmin(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            user.setRole("ADMIN");
            userRepository.save(user);
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(String username) {
        userRepository.findByUsername(username).ifPresent((user -> userRepository.delete(user)));
        return "redirect:/admin/users";
    }
}
