package com.meli.interview.back.subscription_api.controller;


import com.meli.interview.back.subscription_api.datos.UserRequestDTO;
import com.meli.interview.back.subscription_api.service.UserServiceImpl;
import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.datos.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "/login")
    public String login(@RequestBody UserRequestDTO usuario) throws Exception {
     return UserSession.getInstance().obtenerToken(userService,usuario);
    }
}
