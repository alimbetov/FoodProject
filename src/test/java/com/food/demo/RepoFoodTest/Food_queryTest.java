package com.food.demo.RepoFoodTest;

import com.food.demo.model.Food;
import com.food.demo.model.User;
import com.food.demo.repository.FoodRepositoryCRUD;
import com.food.demo.repository.UserRepository;
import com.food.demo.services.JpaUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Food_queryTest {

    @Autowired
    JpaUserDetailsService urepo;

    @Autowired
    FoodRepositoryCRUD foodrepo;

    @Autowired
    UserRepository userRepo;

    @Test
    public void getloadUserByUsername() {
        String username = "root";
        User myuser = (User) urepo.loadUserByUsername(username);
        assert (username.equals(myuser.getUsername()));
    }

    @Test
    public void showUserbyid() {
        Long id = 1L;
        User myuser = (User) urepo.loadUserByid(id);
        assert (myuser.getId() == id);
    }

    @Test
    public void showUserFoods() {
        Long id = 1L;
        User myuser = (User) urepo.loadUserByid(id);
        List<Food> foodlist = new ArrayList<>();

        // foodrepo.findUserByStatusAndNameNamedParamsNative(myuser).forEach();

    }

    @Test
    void createFood() {
        UserDetails find = null;
        try {
            find = urepo.loadUserByUsername("test");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (find == null) {
            User newUser = new User();
            newUser.setUsername("test");
            newUser.setPassword("test");
            urepo.addUser(newUser);
        }
        User user = userRepo.findAll().iterator().next();
        Food food = new Food();
        food.setUser(user);
        food.setActive(true);
        food.setFoodName("Шарик");

        try {
            File image = new ClassPathResource("sample.jpg").getFile();
            try (FileInputStream inputStream = new FileInputStream(image)) {
                food.setImage(Files.readAllBytes(image.toPath()));
                Food result = foodrepo.save(food);
                assert (result.getId() != null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
