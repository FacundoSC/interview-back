package com.meli.interview.back.subscription_api.controller;

import com.meli.interview.back.subscription_api.datos.DTO.ResponseInfoDTO;
import com.meli.interview.back.subscription_api.datos.DTO.UserRequestDTO;
import com.meli.interview.back.subscription_api.datos.UserSession;
import com.meli.interview.back.subscription_api.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseInfoDTO> login(@RequestBody UserRequestDTO usuario, HttpServletRequest request) throws Exception {
        UserSession.getInstance().obtenerToken(userService, usuario);
        return ResponseEntity.ok()
                .header("Authorization", UserSession.getInstance().getJwt())
                .body(new ResponseInfoDTO("Logeado exitosamente", request.getServletPath(), HttpStatus.OK.value()));
    }
}
