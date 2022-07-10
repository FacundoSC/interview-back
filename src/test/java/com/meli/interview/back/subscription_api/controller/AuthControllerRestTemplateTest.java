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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class AuthControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate cliente;

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
        ResponseEntity<ResponseInfoDTO> response = cliente
                .postForEntity(crearUri("/api/v1/auth/login"), userRequestDTO, ResponseInfoDTO.class);
        //THEN
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON ,response.getHeaders().getContentType());
        assertEquals("Logeado exitosamente",response.getBody().getMessage());

    }

    private String crearUri(String path) {
        return "http://localhost:" + puerto + path;
    }


}