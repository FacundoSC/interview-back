package com.meli.interview.back.subscription_api.service.impl;

import com.meli.interview.back.subscription_api.datos.DTO.UsernameDto;
import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.datos.UserSession;
import com.meli.interview.back.subscription_api.exception.FriendNotFoundException;
import com.meli.interview.back.subscription_api.exception.UserNotLoggedInException;
import com.meli.interview.back.subscription_api.repository.UserRepository;
import com.meli.interview.back.subscription_api.srcTest.NewUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class SubscriptionServiceImplTest {

    @Autowired
    private SubscriptionServiceImpl subscriptionService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private NewUserTest newUserTest;

    @MockBean
    private UserRepository userRepositoryMock;

    @MockBean
    private UserSession userSessionMock;


    @Test
    void getUserSubscriptionsCost() throws Exception {
        when(userRepositoryMock.save(any())).thenReturn(newUserTest.newUserSesion());
        when(userRepositoryMock.findByUsername("fcordoba")).thenReturn(newUserTest.newUserSesion());
        UserSession.getInstance().obtenerToken(userService, newUserTest.newUserRequestDTOSesion());

        when(userRepositoryMock.findByUsername("mscurrell0@unicef.org")).thenReturn(newUserTest.newUserFriendMicky());


        assertEquals(0.0, subscriptionService.getUserSubscriptionsCost(newUserTest.newUseNameDTOFriendMicky()).floatValue());


        Exception eEntityNotFoundException = assertThrows(EntityNotFoundException.class, () -> {
            subscriptionService.getUserSubscriptionsCost(new UsernameDto());

        });
        assertEquals(eEntityNotFoundException.getMessage(), "El usuario indicado no existe");
        assertTrue(eEntityNotFoundException instanceof EntityNotFoundException);


        Exception eFriendNotFoundException = assertThrows(FriendNotFoundException.class, () -> {
            when(userRepositoryMock.findByUsername("kconnettrr@discovery.com")).thenReturn(newUserTest.newUserFriendKonstantine());
            subscriptionService.getUserSubscriptionsCost(newUserTest.newUseNameDTOFriendKonstantine());

        });
        assertEquals(eFriendNotFoundException.getMessage(), "El usuario indicado no figura en la lista de amigos.");
        assertTrue(eFriendNotFoundException instanceof FriendNotFoundException);
    }

    }

