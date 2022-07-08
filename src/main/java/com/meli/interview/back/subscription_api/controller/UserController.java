package com.meli.interview.back.subscription_api.controller;

import com.meli.interview.back.subscription_api.datos.DTO.UserResponseDTO;
import com.meli.interview.back.subscription_api.datos.DTO.UsernameDto;
import com.meli.interview.back.subscription_api.datos.User;
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
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getUsers(HttpServletRequest request) {
        String token = jwtUtil.obtainToken(request);
        jwtUtil.validateJWT(token);
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping("/user/save")
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PostMapping("/user/add_friend")
    public ResponseEntity<UserResponseDTO> addFriend(@RequestBody UsernameDto newFriend, HttpServletRequest request) {
        String token = jwtUtil.obtainToken(request);
        jwtUtil.validateJWT(token);
        return ResponseEntity.ok().body(userService.addFriend(newFriend.getUsername()));
    }

    @GetMapping("/user/friends/suscripciones")
    public ResponseEntity<?> suscripciones(@RequestBody UsernameDto user, HttpServletRequest request) throws Exception {
        String token = jwtUtil.obtainToken(request);
        jwtUtil.validateJWT(token);
        return ResponseEntity.status(200).body("" + subscriptionService.getUserSubscriptionsCost(user));
    }

}
