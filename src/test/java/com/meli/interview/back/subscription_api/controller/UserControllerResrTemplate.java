package com.meli.interview.back.subscription_api.controller;

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
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserControllerResrTemplate {

    @Autowired
    private TestRestTemplate client;

    @LocalServerPort
    private int puerto;

    @BeforeEach
    void setUp() {
    }




    @Test
    void getUsers(){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", obtenerToken());
        ResponseEntity<List> entity = new TestRestTemplate()
                .exchange(crearUri("/api/v1/users"), HttpMethod.GET, new HttpEntity<Object>(headers),
                        List.class);
        assertNotNull(entity);
        assertFalse(entity.getBody().isEmpty());
    }

    private String crearUri(String path) {
        return "http://localhost:" + puerto + path;
    }

    public String obtenerToken(){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("fcordoba");
        userRequestDTO.setPassword("prueba123");
        ResponseEntity<ResponseInfoDTO> response = client
                .postForEntity(crearUri("/api/v1/auth/login"), userRequestDTO, ResponseInfoDTO.class);
        HttpHeaders responseHeaders = response.getHeaders();
        return "Bearer "+responseHeaders.get("Authorization").get(0);
    }
}
