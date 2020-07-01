package com.food.demo.RepoFoodTest;

import com.food.demo.model.Food;
import com.food.demo.model.User;
import com.food.demo.repository.FoodRepositoryCRUD;
import com.food.demo.services.JpaUserDetailsService;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Food_queryTest {

    @Autowired
    JpaUserDetailsService urepo;

    @Autowired
    FoodRepositoryCRUD foodrepo;


 @Test
 public void getloadUserByUsername(){
     String username="root";
    User myuser = (User) urepo.loadUserByUsername(username);
     assert (username.equals(myuser.getUsername()));
 }

    @Test
    public void showUserbyid(){
       Long id=1L;
        User myuser = (User) urepo.loadUserByid(id);
        assert (myuser.getId()==id);
    }

    @Test
    public void showUserFoods(){
        Long id=1L;
        User myuser = (User) urepo.loadUserByid(id);
        List<Food> foodlist = new ArrayList<>();

       // foodrepo.findUserByStatusAndNameNamedParamsNative(myuser).forEach();

    }


}
