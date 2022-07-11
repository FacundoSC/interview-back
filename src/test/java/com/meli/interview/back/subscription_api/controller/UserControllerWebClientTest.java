package com.meli.interview.back.subscription_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.interview.back.subscription_api.datos.DTO.ResponseInfoDTO;
import com.meli.interview.back.subscription_api.datos.DTO.UserRequestDTO;
import com.meli.interview.back.subscription_api.datos.DTO.UserResponseDTO;
import com.meli.interview.back.subscription_api.datos.DTO.UsernameDto;
import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerWebClientTest {
    @Autowired
    private UserService userService;
    @Autowired
    private WebTestClient client;

    @LocalServerPort
    private int puerto;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
         mapper = new ObjectMapper();
    }

    @Test
    void getUsers() {
       client.method(HttpMethod.GET).uri(crearUri("/api/v1/users")).
               contentType(MediaType.APPLICATION_JSON).header("Authorization",obtenerToken())
               .exchange().expectStatus().isOk()
              .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void saveUser() {
        User user = new User();
        user.setPassword("prueba123");
        user.setUsername("pepito@gmail.com");
        user.setName("PruebaSave");
        client.post()
                .uri("/api/v1/user/save")
                .bodyValue(user)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo("PruebaSave");
    }

    @Test
    void addFriend() {
        UsernameDto  friend = new UsernameDto();
        friend.setUsername("mscurrell0@unicef.org");
        client.post()
                .uri("/api/v1/user/add_friend").header("Authorization",obtenerToken())
                .bodyValue(friend)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class).consumeWith(
                        response->{
                            try {
                                JsonNode jsonNode = mapper.readTree(response.getResponseBody());
                                assertNotNull(jsonNode);
                                assertEquals(jsonNode.path("username").toString().replace("\"",""),"fcordoba");
                                final List<String> friends = new ArrayList<>();
                                jsonNode.path("friendsUsername").forEach(node -> friends.add(node.asText()));
                                assertEquals(friends.get(0),"mscurrell0@unicef.org");
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
    }

    @Test
    void suscripciones() {
    }



    private String crearUri(String path) {
        return "http://localhost:" + puerto + path;
    }

    public String obtenerToken(){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("fcordoba");
        userRequestDTO.setPassword("prueba123");
        FluxExchangeResult<ResponseInfoDTO> objectFluxExchangeResult = client.post()
                .uri(crearUri("/api/v1/auth/login"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequestDTO).exchange().returnResult(ResponseInfoDTO.class);
        HttpHeaders responseHeaders = objectFluxExchangeResult.getResponseHeaders();
        return "Bearer "+responseHeaders.get("Authorization").get(0);
    }





}