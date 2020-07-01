package com.food.demo.controllers;

import com.food.demo.model.User;
import com.food.demo.repository.UserRepository;
import com.food.demo.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping()
public class ProfileController {

    @Autowired
    JpaUserDetailsService jpaUserDetailsService;



    @GetMapping(value = {"/profile"})
    public String showCustomer(Model model) {

        UserDetails d = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userFromDB = (User) jpaUserDetailsService.loadUserByUsername(d.getUsername());


        if (userFromDB != null) {
            userFromDB.setPassword("********************");
            model.addAttribute("u", userFromDB);
        }
        return "profile";
    }


    @PostMapping(value = "/profileEdit")
    public String editUser(Model model,
                           @ModelAttribute("u") @Valid User user,
                           BindingResult bindingResult) {
        String errorMessage;


        try {
            UserDetails d = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User userFromDB = (User) jpaUserDetailsService.loadUserByUsername(d.getUsername());
            if (userFromDB != null) {
                userFromDB.setCompanyTitle(user.getCompanyTitle());
                userFromDB.setEmail(user.getEmail());
                jpaUserDetailsService.save(userFromDB);
                model.addAttribute("u", userFromDB);
            }

        } catch (Exception ex) {
            errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "profile";
        }
        return "profile";
    }


}
