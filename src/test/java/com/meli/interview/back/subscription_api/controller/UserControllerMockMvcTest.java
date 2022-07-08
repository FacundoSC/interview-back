package com.meli.interview.back.subscription_api.controller;
import com.meli.interview.back.subscription_api.datos.DTO.UserResponseDTO;
import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.service.UserService;
import com.meli.interview.back.subscription_api.util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


    @WebMvcTest(UserController.class)


public class UserControllerMockMvcTest {
/*
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JWTUtil jwtUtil;

    @Test
    void name() throws Exception {
        when(userService.findAll()).thenReturn((List<UserResponseDTO>) Arrays.asList(new User()));
        mvc.perform(get("/api/v1/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }*/
}
