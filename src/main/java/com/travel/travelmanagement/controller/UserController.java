package com.travel.travelmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.travel.travelmanagement.entity.UserEntity;
import com.travel.travelmanagement.service.JwtService;
import com.travel.travelmanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public UserEntity register(@RequestBody UserEntity user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity user) {
        UserEntity authenticatedUser = userService.login(user.getEmail(), user.getPassword());
        String token = jwtService.generateToken(authenticatedUser.getEmail());
        
        return ResponseEntity.ok(Map.of(
            "token", token,
            "user", authenticatedUser
        ));
    }

    @GetMapping
    public List<UserEntity> getUsers() {
        return userService.getUsers();
    }
}