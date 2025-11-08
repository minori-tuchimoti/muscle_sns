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

  @GetMapping("/edit/{id}")
  public String editPost(@PathVariable Long id, Model model) {
    Post post = postRepository.findById(id).orElseThrow();
    model.addAttribute("post", post);
    return "edit_post";
  }

  @PostMapping("/update/{id}")
  public String updatePost(@PathVariable Long id, @RequestParam String content) {
    Post post = postRepository.findById(id).orElseThrow();
    post.setContent(content);
    postRepository.save(post);
    return "redirect:/home";
  }

  @PostMapping("/delete/{id}")
  public String deletePost(@PathVariable Long id) {
    postRepository.deleteById(id);
    return "redirect:/home";
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
