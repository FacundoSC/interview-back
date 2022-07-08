package com.meli.interview.back.subscription_api.controller;


import com.meli.interview.back.subscription_api.datos.DTO.UserRequestDTO;
import com.meli.interview.back.subscription_api.service.impl.UserServiceImpl;
import com.meli.interview.back.subscription_api.datos.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDTO usuario) throws Exception {
        UserSession.getInstance().obtenerToken(userService, usuario);
        return ResponseEntity.ok().header("Authorization", UserSession.getInstance().getJwt()).body("Logueado exitosamente");
    }
}
