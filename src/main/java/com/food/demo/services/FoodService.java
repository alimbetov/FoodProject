package com.food.demo.services;

import com.food.demo.model.Food;
import com.food.demo.model.User;
import com.food.demo.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    FoodRepository foodRepository;

    public List<Food> getAllFoods() {
        List<Food> foodlist = (List<Food>) foodRepository.findAll();
        if(foodlist.size() > 0) {
            return foodlist;
        } else {
            return new ArrayList<Food>();
        }
    }

    public Food  getFoodById(Long id) {
        Optional<Food> food = foodRepository.findById(id);
        if(food.isPresent()) {
            return food.get();
        }
        return null;
    }


    public Food createOrUpdateFood(Food entity)
    {
        Optional<Food> myFood = foodRepository.findById(entity.getId());

        if(myFood.isPresent())
        {
            Food newEntity = myFood.get();

            newEntity.setActive(entity.isActive());
            newEntity.setFoodCategory(entity.getFoodCategory());
            newEntity.setFoodDescription(entity.getFoodDescription());
            newEntity.setFoodName(entity.getFoodName());
            newEntity.setUser(entity.getUser());
            newEntity.setImage(entity.getImage());
            newEntity.setPrice(entity.getPrice());
            newEntity.setWeight(entity.getWeight());

            newEntity = foodRepository.save(newEntity);

            return newEntity;
        } else {
            entity = foodRepository.save(entity);

            return entity;
        }
    }

    public void deleteFoodById(Long id)
    {
        Optional<Food> food = foodRepository.findById(id);
        if(food.isPresent())
        {
            foodRepository.deleteById(id);
        }
    }




    private boolean existsById(Long id) {
        return foodRepository.existsById(id);
    }


    public List<Food> findAll(int pageNumber, int rowPerPage) {
        List<Food> foodlist = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage,
                Sort.by("id").ascending());
        foodRepository.findAll(sortedByIdAsc).forEach(foodlist::add);
        return foodlist;
    }

    public  Food save(Food food)  {
        if (!StringUtils.isEmpty(food.getFoodName())) {
            if (existsById(food.getId())) {
                System.out.println ("State with id: " + food.getId() + " already exists");
            }
            return foodRepository.save(food);
        }
        else {
            System.out.println ("Failed to save  is null or empty");
        }
        return  null;

    }

    public void  update(Food food) {
        if (!StringUtils.isEmpty(food.getFoodName())) {
            if (!existsById(food.getId())) {
                System.out.println("Cannot find Contact with id: " + food.getId());
            }
            foodRepository.save(food);

        } else {
            System.out.println("Failed to save  is null or empty");
        }
    }


    public Long count() {
        return foodRepository.count();
    }


    public List<Food> findAll() {
        List<Food> foodlist = new ArrayList<>();
        foodRepository.findAll().forEach(foodlist::add);
        return foodlist;
    }

    public List<Food> getfoodsByCustomer( User inuser) {
        List<Food> foodlist = new ArrayList<>();
       // foodRepository.findAllByUserAndActiveTrue(inuser).forEach(foodlist::add);
        return foodlist;
    }





}
