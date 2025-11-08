package com.example.muscle_sns.controller;

import com.example.muscle_sns.entity.User;
import com.example.muscle_sns.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;


import java.io.File;
import java.io.IOException;

@Controller
public class UserController{

  private final UserRepository userRepository;

  private final String uploadDir = "C:/Users/minor/OneDrive/デスクトップ/projects/muscle-sns/uploads/";

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/mypage/edit")
  public String editProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    User user = userRepository.findByUsername(userDetails.getUsername());
    model.addAttribute("user", user);
    return "edit_profile";
  }

  @PostMapping("/mypage/update")
  public String updateProfile(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestParam String bio,
      @RequestParam String hobbies,
      @RequestParam Integer age,
      @RequestParam Double height,
      @RequestParam Double weight,
      @RequestParam String location
  ) {
      User user = userRepository.findByUsername(userDetails.getUsername());

      user.setBio(bio);
      user.setHobbies(hobbies);
      user.setAge(age);
      user.setHeight(height);
      user.setWeight(weight);
      user.setLocation(location);

      userRepository.save(user);

      return "redirect:/mypage";
  }

  @PostMapping("/mypage/upload")
  public String uploadProfileImage(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("image") MultipartFile file) throws IOException {

    if(!file.isEmpty()) {

      File uploadPath = new File(uploadDir);
      if (!uploadPath.exists()) {
          uploadPath.mkdirs();
      }
      
      String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      File dest = new File(uploadDir + fileName);
      file.transferTo(dest);

      User user = userRepository.findByUsername(userDetails.getUsername());
      user.setProfileImage(fileName);
      userRepository.save(user);
    }

    return "redirect:/mypage";
  }

  @PostMapping("/mypage/delete")
  public String deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
    if(userDetails != null) {
      User user = userRepository.findByUsername(userDetails.getUsername());
      userRepository.delete(user);
      SecurityContextHolder.clearContext();
    }

    return "redirect:/login";
  }
  
}