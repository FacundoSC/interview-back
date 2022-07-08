package com.meli.interview.back.subscription_api.service.impl;

import com.meli.interview.back.subscription_api.datos.DTO.UserRequestDTO;
import com.meli.interview.back.subscription_api.datos.DTO.UserResponseDTO;
import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.datos.UserSession;
import com.meli.interview.back.subscription_api.exception.UserNotFoundException;
import com.meli.interview.back.subscription_api.exception.UserNotLoggedInException;
import com.meli.interview.back.subscription_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepositoryMock;

    @MockBean
    private UserSession userSession;


    @Test
    void save() {
        when(userRepositoryMock.save(any())).thenReturn(new User("PruebaSave","PruebaSave","PruebaSave"));
        assertEquals("PruebaSave", userService.save(new User()).getName());
        assertTrue(userService.save(new User()) instanceof UserResponseDTO);

        verify(userRepositoryMock, times(2)).save(new User());


    }

    @Test
    void findAll() {

        User userUno = new User("PruebaUno","PruebaUno","PruebaUno");
        User userDos = new User("PruebaDos","PruebaDos","PruebaDos");
        List<User> userListUno = Arrays.asList(userDos);
        List<User> userListDos = Arrays.asList(userUno);
        userUno.setFriends(userListUno);
        userDos.setFriends(userListDos);
        List<User> userList = new ArrayList<>();
        userList.add(userUno);
        userList.add(userDos);


        when(userRepositoryMock.findAll()).thenReturn(userList);


        assertNotNull(userService.findAll());
        assertTrue(userService.findAll().size() > 1);
        assertEquals("PruebaUno", userService.findAll().get(0).getName());
        assertTrue(userService.findAll().get(0) instanceof  UserResponseDTO );

        verify(userRepositoryMock, times(4)).findAll();


    }

    @Test
    void obtenerUsuarioPorCredenciales() {
        User userUno = new User("PruebaUno","PruebaUno","PruebaUno");
        UserRequestDTO userRequestDTO=new UserRequestDTO();
        userRequestDTO.setUsername("PruebaUno");
        userRequestDTO.setPassword("PruebaUno");
        when(userRepositoryMock.findByUsername(any())).thenReturn(userUno);

        assertEquals("PruebaUno", userService.obtenerUsuarioPorCredenciales(userRequestDTO).getName());
        assertTrue(userService.obtenerUsuarioPorCredenciales(userRequestDTO) instanceof User);

        Exception e = assertThrows(UserNotLoggedInException.class, () -> {
            userService.obtenerUsuarioPorCredenciales(new UserRequestDTO());

        });
        assertEquals(e.getMessage() ,  "Usuario o contraseña inválidos");
        assertTrue(e instanceof UserNotLoggedInException);


        verify(userRepositoryMock, times(2)).findByUsername(userRequestDTO.getUsername());
    }

    @Test
    void getUserByUsername() {
        User userUno = new User("PruebaUno","PruebaUno","PruebaUno");
        when(userRepositoryMock.findByUsername(any())).thenReturn(userUno);

        assertEquals("PruebaUno", userService.getUserByUsername("PruebaUno").getName());
        assertTrue(userService.getUserByUsername("PruebaUno") instanceof User);
        verify(userRepositoryMock, times(2)).findByUsername("PruebaUno");

        when(userRepositoryMock.findByUsername(any())).thenReturn(null);
        Exception e = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByUsername("Hola");

        });

        assertEquals(e.getMessage() ,  "El usuario perteneciente al token no existe");
        assertTrue(e instanceof UserNotFoundException);
    }

    @Test
    void addFriend() {
        User newFriend = new User("Craggie","PruebaUno","PruebaUno");
        User currentUser = new User("PruebaUno","PruebaUno","PruebaUno");
        when(UserSession.getInstance().getLoggedUser()).thenReturn(currentUser);
        when(userRepositoryMock.save(newFriend)).thenReturn(newFriend);
        when(userRepositoryMock.save(currentUser)).thenReturn(currentUser);

        assertEquals("PruebaUno", userService.addFriend("Craggie").getName());
       // when(userRepositoryMock.findByUsername(any())).thenReturn(userUno);
    }

    @Test
    void convertFriendList() {
    }
}