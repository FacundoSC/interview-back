package com.meli.interview.back.subscription_api.controller;
import com.meli.interview.back.subscription_api.datos.DTO.UserResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.interview.back.subscription_api.datos.Subscription;
import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.service.SubscriptionService;
import com.meli.interview.back.subscription_api.service.UserService;
import com.meli.interview.back.subscription_api.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(UserController.class)
public class UserControllerMockMvcTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;

    @MockBean
    private SubscriptionService subscriptionServiceService;

    @MockBean
    private JWTUtil jwtUtil;

    private  ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void saveUsuarioTest() throws Exception {
        User usuario = new User("name","username", "3214");
        UserResponseDTO userResponseDTO = new UserResponseDTO("name","username",new ArrayList<String>(),new ArrayList<Subscription>());
        when(userService.save(any())).thenReturn(userResponseDTO);
        mvc.perform(post("/api/v1/user/save").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(usuario)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.username").value("username"));
    }

     //TODO  nos debe interesar  probar el service no como se genera o valida el token

        @Test
        void listarUserResponseTest() throws Exception {
            List<UserResponseDTO> usersResponseDTO = new ArrayList<>();
            UserResponseDTO userResponseDTO = new UserResponseDTO("Facundo","fcordoba");
            usersResponseDTO.add(userResponseDTO);
            when(userService.findAll()).thenReturn(usersResponseDTO);
            mvc.perform(get("/api/v1/users").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name").value("Facundo"))
                    .andExpect(jsonPath("$[0].username").value("fcordoba"));
        }


}
