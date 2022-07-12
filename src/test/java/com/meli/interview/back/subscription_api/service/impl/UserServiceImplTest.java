package com.meli.interview.back.subscription_api.service.impl;

import com.meli.interview.back.subscription_api.datos.DTO.UserRequestDTO;
import com.meli.interview.back.subscription_api.datos.DTO.UserResponseDTO;
import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.datos.UserSession;
import com.meli.interview.back.subscription_api.exception.UserNotFoundException;
import com.meli.interview.back.subscription_api.exception.UserNotLoggedInException;
import com.meli.interview.back.subscription_api.repository.UserRepository;
import com.meli.interview.back.subscription_api.srcTest.NewUserTest;
import com.meli.interview.back.subscription_api.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private NewUserTest newUserTest;

    @MockBean
    private UserRepository userRepositoryMock;

    @MockBean
    private UserSession userSessionMock;

    @Test
    void save() {
        when(userRepositoryMock.save(any())).thenReturn(newUserTest.newUserFriendMicky());
        assertEquals("Micky", userService.save(new User()).getName());
        assertTrue(userService.save(new User()) instanceof UserResponseDTO);

        verify(userRepositoryMock, times(2)).save(new User());


    }

    @Test
    void findAll() {

        when(userRepositoryMock.findAll()).thenReturn(newUserTest.newUserList());

        assertNotNull(userService.findAll());
        assertTrue(userService.findAll().size() > 1);
        assertEquals("Konstantine", userService.findAll().get(0).getName());
        assertTrue(userService.findAll().get(0) instanceof UserResponseDTO);

        verify(userRepositoryMock, times(4)).findAll();


    }

    @Test
    void obtenerUsuarioPorCredenciales() {

        when(userRepositoryMock.findByUsername(any())).thenReturn(newUserTest.newUserFriendMicky());

        assertEquals("Micky", userService.obtenerUsuarioPorCredenciales(newUserTest.newUserRequestDTOMicky()).getName());
        assertTrue(userService.obtenerUsuarioPorCredenciales(newUserTest.newUserRequestDTOMicky()) instanceof User);

        Exception e = assertThrows(UserNotLoggedInException.class, () -> {
            userService.obtenerUsuarioPorCredenciales(new UserRequestDTO());

        });
        assertEquals(e.getMessage(), "Usuario o contraseña inválidos");
        assertTrue(e instanceof UserNotLoggedInException);


        verify(userRepositoryMock, times(2)).findByUsername((newUserTest.newUserRequestDTOMicky()).getUsername());
    }

    @Test
    void getUserByUsername() {

        when(userRepositoryMock.findByUsername(any())).thenReturn(newUserTest.newUserFriendMicky());

        assertEquals(newUserTest.newUserFriendMicky().getName(), userService.getUserByUsername(newUserTest.newUserFriendMicky().getUsername()).getName());
        assertTrue(userService.getUserByUsername(any()) instanceof User);
        verify(userRepositoryMock, times(1)).findByUsername(newUserTest.newUserFriendMicky().getUsername());

        when(userRepositoryMock.findByUsername(any())).thenReturn(null);
        Exception e = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByUsername("Hola");

        });

        assertEquals(e.getMessage(), "El usuario perteneciente al token no existe");
        assertTrue(e instanceof UserNotFoundException);
    }

    @Test
    void addFriend() throws Exception {
        when(userRepositoryMock.save(any())).thenReturn(newUserTest.newUserSesion());
        when(userRepositoryMock.findByUsername("fcordoba")).thenReturn(newUserTest.newUserSesion());
        UserSession.getInstance().obtenerToken(userService, newUserTest.newUserRequestDTOSesion());

        when(userRepositoryMock.findByUsername("mscurrell0@unicef.org")).thenReturn(newUserTest.newUserFriendMicky());
        when(userRepositoryMock.save(newUserTest.newUserFriendMicky())).thenReturn(newUserTest.newUserFriendMicky());

        assertEquals("Facundo", userService.addFriend("mscurrell0@unicef.org").getName());
        assertTrue(userService.addFriend("mscurrell0@unicef.org") instanceof UserResponseDTO);



    }


}