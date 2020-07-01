package com.food.demo.rest_controllers;

import com.food.demo.model.Food;
import com.food.demo.model.User;
import com.food.demo.repository.UserRepository;
import com.food.demo.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/foods")
public class restFoodController {

    @Autowired
    FoodService foodService;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods() {
        List<Food> list = foodService.findAll();
        return new ResponseEntity<List<Food>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/food/{id}")
    public ResponseEntity<Food> getContainerById(@PathVariable("id") Long id) {
        Food entity = foodService.getFoodById(id);
        return new ResponseEntity<Food>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Food> createOrUpdateContainer(Food food) {
        Food updated = foodService.createOrUpdateFood(food);
        return new ResponseEntity<Food>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/food{id}")
    public HttpStatus deleteContainerById(@PathVariable("id") Long id) {
        foodService.deleteFoodById(id);
        return HttpStatus.FORBIDDEN;
    }

    @RequestMapping(path = "/foods/custumer{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Food>> getFoodByCustomer(@PathVariable Long id) {
      User userfromdb=  userRepository.findById(id).get();
        List<Food> list=new ArrayList<>();
              if (userfromdb!=null){
                  list=foodService.getfoodsByCustomer(userfromdb);

              }
        return new ResponseEntity<List<Food>>(list, new HttpHeaders(), HttpStatus.OK);
    }



}