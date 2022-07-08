package com.meli.interview.back.subscription_api.controller;

import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerWebClientTest {
/*
    @Autowired
    private UserService userService;

    @Autowired
    private WebTestClient client;

    User user = new User("PruebaSave", "PruebaSave", "PruebaSave");

    @Test
    void getUsers() {
        client.get()
                .uri("/api/v1/users")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(User.class).isEqualTo(userService.findAll());
    }

    @Test
    void saveUser() {


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
        User user2 = new User("PruebaFriends", "PruebaFriends", "PruebaFriends");

        List<User> listFriends = new ArrayList<>();
        listFriends.add(new User("Friend1", "Friend1", "Friend1"));
        user2.setFriends(listFriends);
        client.post()
                .uri("/api/v1/user/friends")
                .bodyValue(user2)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.friends.name").isEqualTo("Friend1");
    }

    @Test
    void suscripciones() {
    }*/
}