package com.food.demo.repository;

import com.food.demo.model.Food;
import com.food.demo.model.User;
import com.food.demo.model.enums.FoodCategory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository  extends PagingAndSortingRepository<Food, Long>, JpaSpecificationExecutor<Food> {

 // List<Food> findAllByUserAndActiveTrue(User user);

 //List<Food> findAllByUserAndFoodCategoryAndActiveTrue(User user, FoodCategory foodCategory);



}