package com.food.demo.controllers;



import com.food.demo.model.Food;
import com.food.demo.model.User;
import com.food.demo.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
@Controller
public class ImageController {

@Autowired
FoodRepository Foodrepo;

    @RequestMapping(value = "/image/{image_id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("image_id") Long imageId) throws IOException {
        Food food =Foodrepo.findById(imageId).get();
        final String encodedString = food.getImage().toString();
        final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);
        byte[] imageContent = Foodrepo.findById(imageId).get().getImage();;
        byte[] imageContent_base64D = Base64.getDecoder().decode(pureBase64Encoded);
        byte[] imageContent_base64E = Base64.getEncoder().encode(imageContent);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(imageContent_base64E, headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/image2/{image_id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage2(@PathVariable("image_id") Long imageId) throws IOException {
        Food food =Foodrepo.findById(imageId).get();
        byte[] imageContent_base64E = Base64.getEncoder().encode(food.getImage());
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(imageContent_base64E, headers, HttpStatus.OK);
    }




    @GetMapping("food/display/{id}")
    @ResponseBody
    public void showImage(@PathVariable Long id, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("image/jpeg,image/jpg,image/gif,image/png");
        response.getOutputStream().write(Foodrepo.findById(id).get().getImage());
        response.getOutputStream().close();
    }


}
