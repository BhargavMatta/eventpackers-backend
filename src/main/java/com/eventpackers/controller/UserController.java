package com.eventpackers.controller;

import com.eventpackers.model.User;
import com.eventpackers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User loginData) {
        return userRepository.findByMobile(loginData.getMobile())
                .map(user -> {
                    if (user.getPassword().equals(loginData.getPassword())) {
                        return "Login successful!";
                    } else {
                        return "Invalid password!";
                    }
                })
                .orElse("User not found!");
    }
}
