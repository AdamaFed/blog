package com.team3.blogteam3.session;

import com.team3.blogteam3.session.Session;
import com.team3.blogteam3.session.SessionRepository;
import com.team3.blogteam3.user.User;
import com.team3.blogteam3.user.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.Optional;

@Controller
public class LoginController {

    private SessionRepository sessionRepository;
    private UserRepository userRepository;

    public LoginController(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/login")
    public String getLoginPage(){
        return "login";
    }
    @PostMapping("/login")
    public String login(HttpServletResponse response, @RequestParam String username, @RequestParam String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {

            User dbUser = optionalUser.get();
            System.out.println("Retrieved password from DB: " + dbUser.getPassword());

            Boolean isPasswordMatch = BCrypt.checkpw(password.trim(), dbUser.getPassword().trim());

            if(isPasswordMatch) {
                Session session = new Session(optionalUser.get(), Instant.now().plusSeconds(7 * 24 * 60 * 60));
                sessionRepository.save(session);
                Cookie cookie = new Cookie("sessionId", session.getId());
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/home";
            }
        }
        return "login";
    }

    @GetMapping("/logout")
    public String getLogoutPage() {
        return "logout";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response, @CookieValue(value = "sessionId", defaultValue = "") String sessionId) {
        sessionRepository.deleteById(sessionId);

        Cookie cookie = new Cookie("sessionId", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/home";
    }
}