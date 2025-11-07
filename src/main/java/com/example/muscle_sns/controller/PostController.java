package com.example.muscle_sns.controller;

import com.example.muscle_sns.entity.Post;
import com.example.muscle_sns.entity.User;
import com.example.muscle_sns.repository.PostRepository;
import com.example.muscle_sns.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {

  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public PostController(PostRepository postRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  @GetMapping("/new")
  public String showPostForm(Model model) {
    model.addAttribute("post", new Post());
    return "post_form";
  }

  @PostMapping("/create")
  public String createPost(@ModelAttribute Post post, @AuthenticationPrincipal UserDetails userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername());
    post.setUser(user);
    postRepository.save(post);
    return "redirect:/home";
  }
}
