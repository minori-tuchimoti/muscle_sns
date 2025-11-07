package com.example.muscle_sns.service;

import com.example.muscle_sns.entity.User;
import com.example.muscle_sns.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void registerUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }
}