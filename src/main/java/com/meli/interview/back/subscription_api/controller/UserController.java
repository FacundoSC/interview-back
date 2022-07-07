package com.meli.interview.back.subscription_api.controller;


import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.service.UserService;
import com.meli.interview.back.subscription_api.service.impl.SubscriptionService;
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
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(HttpServletRequest request) {
        String token = jwtUtil.obtainToken(request);
        jwtUtil.validateJWT(token);
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PostMapping("/user/friends")
    public ResponseEntity<User> addFriend(@RequestBody User user, HttpServletRequest request) {
        String token = jwtUtil.obtainToken(request);
        jwtUtil.validateJWT(token);
        return ResponseEntity.ok().body(userService.save(user));
    }

    @GetMapping("/user/friends/suscripciones")
    public ResponseEntity<?> suscripciones(@RequestBody User user, HttpServletRequest request) throws Exception {
        String token = jwtUtil.obtainToken(request);
        jwtUtil.validateJWT(token);
        return ResponseEntity.status(200).body("" + subscriptionService.getUserSubscriptionsCost(user));
    }


}
