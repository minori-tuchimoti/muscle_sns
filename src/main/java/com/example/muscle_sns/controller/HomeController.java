package com.example.muscle_sns.controller;

import com.example.muscle_sns.entity.Post;
import com.example.muscle_sns.repository.PostRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

  private final PostRepository postRepository;

  public HomeController(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @GetMapping("/home")
  public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    if(userDetails != null) {
      model.addAttribute("username", userDetails.getUsername());
    }

    List<Post> posts = postRepository.findAll();
    model.addAttribute("posts", posts);

    return "home";
  }
  
}
