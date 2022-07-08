package com.meli.interview.back.subscription_api.controller;

import com.meli.interview.back.subscription_api.repository.UserRepository;
import com.meli.interview.back.subscription_api.service.SubscriptionService;
import com.meli.interview.back.subscription_api.service.UserService;
import com.meli.interview.back.subscription_api.datos.Role;
import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private  UserService userService;

    @Autowired
    private JWTUtil jwtUtil;
    private SubscriptionService subscriptionService = new SubscriptionService();

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
      //  String token = request.getHeader("authorization").split(" ")[1];
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PostMapping("/user/friends")
    public ResponseEntity<User> addFriend(@RequestBody User user ){
    //    String token = request.getHeader("authorization");
        return ResponseEntity.ok().body(userService.save(user));
    }

    @GetMapping("/user/friends/suscripciones")
    public ResponseEntity<?> suscripciones(@RequestBody User user ,HttpServletRequest request) throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmY29yZG9iYSIsImlhdCI6MTY1Njk1OTk1MCwic3ViIjoiIiwiaXNzIjoiTWFpbiIsImV4cCI6MTY1NzU2NDc1MH0.bEeuRlpfiOd3sG4Hvz1E6CSrQJS4NkoVlLH0LQ-RRHQ";//request.getHeader("authorization");
        subscriptionService.setJwt(token);
        return ResponseEntity.status(200).body(""+subscriptionService.getUserSubscriptionsCost(user));
    }








}
