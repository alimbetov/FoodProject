package com.food.demo.controllers;

import com.food.demo.model.Food;
import com.food.demo.repository.FoodRepositoryCRUD;
import com.food.demo.services.FoodService;
import com.food.demo.utils.FoodImage;
import com.food.demo.utils.ImageUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

@Controller
public class FileUploadController {


    @Autowired
    private FoodService foodService;




        @PostMapping("/uploadImage")////new annotation since 4.3
        public String singleFileUpload(Model model,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestParam("id") Long id,
                                       RedirectAttributes redirectAttributes) {

            System.out.println("+++++++++++++++++++++++++++++++++++"+id);

            if (file.isEmpty()) {
                model.addAttribute("errorMessage", "File not found");
                model.addAttribute("standardDate", new Date());
                model.addAttribute("localDateTime", LocalDateTime.now());
                model.addAttribute("localDate", LocalDate.now());
                model.addAttribute("timestamp", Instant.now());
                model.addAttribute("imgUtil", new ImageUtil());
                return "redirect:/foods/" +id + "/edit";
            }


            Food food = foodService.getFoodById(id);
            File image = (File) file;
            try (FileInputStream inputStream = new FileInputStream((File) file)) {
                food.setImage(Files.readAllBytes(image.toPath()));
                Food result = foodService.save(food);

                model.addAttribute("standardDate", new Date());
                model.addAttribute("localDateTime", LocalDateTime.now());
                model.addAttribute("localDate", LocalDate.now());
                model.addAttribute("timestamp", Instant.now());
                model.addAttribute("imgUtil", new ImageUtil());

                return "redirect:/foods/" + String.valueOf(food.getId()) + "/edit";
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            model.addAttribute("standardDate", new Date());
            model.addAttribute("localDateTime", LocalDateTime.now());
            model.addAttribute("localDate", LocalDate.now());
            model.addAttribute("timestamp", Instant.now());
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("errorMessage", "Food not found");

            return "redirect:/foods/" +id + "/edit";
        }


    @GetMapping(value = {"/fotoloader/{foodid}/edit"})
    public String showEditState(Model model, @PathVariable long foodid) {
        Food food = null;
        FoodImage fm=new FoodImage();
            food = foodService.getFoodById(foodid);
            fm.setId(food.getId());
            fm.setFoodName(food.getFoodName());
            fm.setImage(food.getImage());
            model.addAttribute("imgUtil", new ImageUtil());

            model.addAttribute("FoodImage",fm);
        return "food-imageupload";
    }
    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadmyfile", method = RequestMethod.POST)
    String uploadFileHandler(Model model,
                             @RequestParam("id") Long id,
                             @RequestParam("file") MultipartFile file) {



        if (file.isEmpty()) {
            System.out.println("uplod myfil  is empty *******");
        }
        if (!file.isEmpty()) {
            System.out.println("uplod myfil  is not empty *******");
        }

        return "redirect:/fotoloader/" + String.valueOf(id) + "/edit";
    }

    @PostMapping("/uploadOneFile")
    public String fileUpload(@ModelAttribute("FoodImage") FoodImage foodImage,
                             @RequestParam("file") MultipartFile file) {

        byte[] image = new byte[0];
        try {
            image = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/fotoloader/" + String.valueOf(foodImage.getId()) + "/edit";
        }
        try {
            image = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/fotoloader/" + String.valueOf(foodImage.getId()) + "/edit";
        }
        if (image!=null) {
                Food food = foodService.getFoodById(foodImage.getId());
                food.setImage(image);
                foodService.save(food);
            }

        return "redirect:/fotoloader/" + String.valueOf(foodImage.getId()) + "/edit";
    }




}