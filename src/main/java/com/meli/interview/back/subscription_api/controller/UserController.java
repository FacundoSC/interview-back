package com.meli.interview.back.subscription_api.controller;

import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.datos.UsernameDto;
import com.meli.interview.back.subscription_api.service.SubscriptionService;
import com.meli.interview.back.subscription_api.service.UserService;
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

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(HttpServletRequest request) {
        String token = request.getHeader("authorization").split(" ")[1];
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PostMapping("/user/friends")
    public ResponseEntity<User> addFriend(@RequestBody User user , HttpServletRequest request){
        String token = request.getHeader("authorization");
        return ResponseEntity.ok().body(userService.save(user));
    }

    @GetMapping("/user/friends/suscripciones")
    public ResponseEntity<?> suscripciones(@RequestBody UsernameDto user , HttpServletRequest request) throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJrbGF0aGFtN0B0d2l0dGVyLmNvbSIsImlhdCI6MTY1NzIwMTUyMCwic3ViIjoia2xhdGhhbTdAdHdpdHRlci5jb20iLCJpc3MiOiJNYWluIiwiZXhwIjoxNjU3ODA2MzIwfQ.tElF55TSlELAfdS7eGwu4LjSp5IIX0f-3vglvSwfwRY";//request.getHeader("authorization");
        subscriptionService.setJwt(token);
       // return ResponseEntity.status(200).body(""+ subscriptionServiceImpl.getUserSubscriptionsCost(user));
        return ResponseEntity.status(200).body(""+ subscriptionService.getUserSubscriptionsCost(user));
    }
}
