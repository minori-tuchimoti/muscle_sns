package com.example.muscle_sns.controller;

import com.example.muscle_sns.entity.User;
import com.example.muscle_sns.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

  private final UserService userService;

  public RegisterController(UserService userService) {
    this.userService = userService;
  }
  
  @GetMapping("/register")
  public String showRegisterForm(Model model) {
    model.addAttribute("user", new User());
    return "register";
  }

  @PostMapping("/register")
  public String registerUser(@ModelAttribute("user") User user) {
    userService.registerUser(user);
    return "redirect:/login";
  }
}
