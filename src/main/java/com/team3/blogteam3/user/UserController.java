package com.team3.blogteam3.user;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    private UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @GetMapping(value = "/register")
    public String getRegisterPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping(value = "/register")

    public String register(@ModelAttribute("user") User theUser, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }

//        if (userRepository.existsByUsername(theUser.getUsername())) {
//            return "register";
//       }


        String salt = BCrypt.gensalt(10);
        String hashedPassword = BCrypt.hashpw(theUser.getPassword(), salt);

        theUser.setPassword(hashedPassword);
        theUser.setRole("USER");


        userRepository.save(theUser);
        return "successfulregistration";
    }

//    @GetMapping(value = "/successfull")
//    public String getSuccessfull(){
//        return
//
//    }

}
