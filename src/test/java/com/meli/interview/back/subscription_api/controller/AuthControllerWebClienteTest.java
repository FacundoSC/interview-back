package com.meli.interview.back.subscription_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.interview.back.subscription_api.datos.DTO.ResponseInfoDTO;
import com.meli.interview.back.subscription_api.datos.DTO.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class AuthControllerWebClienteTest {

    @Autowired
    private WebTestClient client;

    @LocalServerPort
    private int puerto;

    @BeforeEach
    void setUp() {
    }



    @Test
    void loginTest(){
        // GIVEN
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("fcordoba");
        userRequestDTO.setPassword("prueba123");
        //WHEN
        client.post()
                .uri(crearUri("/api/v1/auth/login"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequestDTO)
        //THEN
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResponseInfoDTO.class)
                .consumeWith(response ->{
                    ResponseInfoDTO responseAuth = response.getResponseBody();
                    assertNotNull(responseAuth);
                    assertEquals("Logeado exitosamente",responseAuth.getMessage());
                });


    }

    private String crearUri(String path) {
        return "http://localhost:" + puerto + path;
    }


}