package com.food.demo.controllers;



import com.food.demo.model.User;
import com.food.demo.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    JpaUserDetailsService jpaUserDetailsService;

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/registration")
    public String registration(Map<String, Object> model) {
        User user= new User();
        model.put("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {

        if(!jpaUserDetailsService.addUser(user)){
            model.addAttribute("message", "User "+ user.getUsername() +" is  exsist!");
            return "registration";
        }
        return "redirect:/login";
    }


}
