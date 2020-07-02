package com.food.demo.controllers;

import com.food.demo.model.Food;
import com.food.demo.model.ImageUtil;
import com.food.demo.model.User;
import com.food.demo.model.enums.FoodCategory;
import com.food.demo.services.FoodService;
import com.food.demo.services.JpaUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/foods/")
public class FoodController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int ROW_PER_PAGE = 10;
    private String title;

    @Autowired
    private FoodService foodService;

    @Autowired
    JpaUserDetailsService jpaUserDetailsService;


    @GetMapping(value = "/list")
    public String getStates_list(Model model,
                                 @RequestParam(value = "page", defaultValue = "1") int pageNumber
    ) {
        List<Food> foods = foodService.findAll(pageNumber, ROW_PER_PAGE);

        long count = foodService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;

        model.addAttribute("listFoods", foods);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "food_list";
    }

    @GetMapping(value = {"/add"})
    public String showAddFood(Model model) {
        Food myFood = new Food();


        UserDetails d = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFromDB = (User) jpaUserDetailsService.loadUserByUsername(d.getUsername());
        if (userFromDB != null) {
            myFood.setUser(userFromDB);
        }

        model.addAttribute("standardDate", new Date());
        model.addAttribute("localDateTime", LocalDateTime.now());
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("timestamp", Instant.now());

        model.addAttribute("add", true);
        model.addAttribute("m_food", myFood);
        return "food-edit";
    }


    @PostMapping(value = "/add")
    public String addFood(Model model,
                          @ModelAttribute("m_food") @Valid Food food,
                          BindingResult bindingResult) {
        String errorMessage;
        Food newState;

        try {

            if (bindingResult.hasErrors()) {

                model.addAttribute("standardDate", new Date());
                model.addAttribute("localDateTime", LocalDateTime.now());
                model.addAttribute("localDate", LocalDate.now());
                model.addAttribute("timestamp", Instant.now());

                model.addAttribute("add", true);
                return "food-edit";
            } else {
                System.out.println( food.toString());
                food.setId(0L);
                UserDetails d = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User userFromDB = (User) jpaUserDetailsService.loadUserByUsername(d.getUsername());
                if (userFromDB != null) {
                    food.setUser(userFromDB);
                }
                newState = foodService.save(food);
                return "redirect:/foods/" + String.valueOf(newState.getId()) + "/edit";
            }


        } catch (Exception ex) {
            // log exception first,
            // then show error
            errorMessage = ex.getMessage();
            model.addAttribute("standardDate", new Date());
            model.addAttribute("localDateTime", LocalDateTime.now());
            model.addAttribute("localDate", LocalDate.now());
            model.addAttribute("timestamp", Instant.now());

            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", true);
            return "food-edit";
        }

    }

    @GetMapping(value = {"/{foodid}/edit"})
    public String showEditState(Model model, @PathVariable long foodid) {
        Food food = null;

        try {
            food = foodService.getFoodById(foodid);
            model.addAttribute("standardDate", new Date());
            model.addAttribute("localDateTime", LocalDateTime.now());
            model.addAttribute("localDate", LocalDate.now());
            model.addAttribute("timestamp", Instant.now());

            model.addAttribute("add", false);
            model.addAttribute("m_food", food);
        } catch (Exception ex) {

            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "food-edit";
    }

    @PostMapping(value = {"/{foodid}/edit"})
    public String updateState(Model model,
                              @PathVariable long foodid,
                              @ModelAttribute("m_food") Food food) {


        try {

            logger.info("updateContact " + foodid);
            food.setId(foodid);

            UserDetails d = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User userFromDB = (User) jpaUserDetailsService.loadUserByUsername(d.getUsername());
            if (userFromDB != null) {
                food.setUser(userFromDB);
            }



            foodService.update(food);
            return "redirect:/foods/" + String.valueOf(food.getId()) + "/edit";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();

            model.addAttribute("standardDate", new Date());
            model.addAttribute("localDateTime", LocalDateTime.now());
            model.addAttribute("localDate", LocalDate.now());
            model.addAttribute("timestamp", Instant.now());

            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "food-edit";
        }
    }

    @GetMapping(value = {"/{foodid}/delete"})
    public String showDeleteStateById(
            Model model, @PathVariable long foodid) {
        Food food = null;

        try {
            food = foodService.getFoodById(foodid);
        } catch (Exception ex) {
            model.addAttribute("standardDate", new Date());
            model.addAttribute("localDateTime", LocalDateTime.now());
            model.addAttribute("localDate", LocalDate.now());
            model.addAttribute("timestamp", Instant.now());

            model.addAttribute("errorMessage", "Food not found");
        }

        model.addAttribute("allowDelete", true);
        model.addAttribute("m_food", food);
        return "food_form";
    }

    @PostMapping(value = {"/{foodid}/delete"})
    public String deleteStateById(
            Model model, @PathVariable long foodid) {

        try {
            foodService.deleteFoodById(foodid);
            return "redirect:/foods/list";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("standardDate", new Date());
            model.addAttribute("localDateTime", LocalDateTime.now());
            model.addAttribute("localDate", LocalDate.now());
            model.addAttribute("timestamp", Instant.now());

            model.addAttribute("errorMessage", errorMessage);
            return "food_form";
        }
    }


}
